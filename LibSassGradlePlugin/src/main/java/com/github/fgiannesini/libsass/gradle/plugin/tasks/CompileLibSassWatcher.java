package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginParameters;

import io.bit3.jsass.CompilationException;

/**
 * Launcher for {@link WatchService}, permitting continuous compilation
 */
public class CompileLibSassWatcher {

	private final Logger logger;
	private final CompileLibSassTaskDelegate compileLibSassTaskDelegate;
	private final Path watchedDirectory;

	public CompileLibSassWatcher(final Logger logger, final Project project) {
		this.logger = logger;
		this.compileLibSassTaskDelegate = new CompileLibSassTaskDelegate(project, this.logger);
		this.watchedDirectory = this.getWatchedDirectory(project);
	}

	public void launchWatcher() {
		try {
			final WatchService watcher = FileSystems.getDefault().newWatchService();

			this.registerFolderAndSubFolders(watcher, this.watchedDirectory);

			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (final InterruptedException ex) {
					this.logger.error("Wath Service interrupted", ex);
					return;
				}

				for (final WatchEvent<?> event : key.pollEvents()) {
					final WatchEvent.Kind<?> kind = event.kind();

					@SuppressWarnings("unchecked")
					final WatchEvent<Path> ev = (WatchEvent<Path>) event;

					final Path dir = (Path) key.watchable();
					final Path modifiedFile = dir.resolve(ev.context());

					this.logger.info(kind.name() + ": " + modifiedFile);

					this.logger.info(modifiedFile.toFile().getAbsolutePath());
					if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE) && modifiedFile.toFile().isDirectory()) {
						this.registerFolderAndSubFolders(watcher, modifiedFile);
					}

					try {
						this.compileLibSassTaskDelegate.compile();
					} catch (final CompilationException ce) {
						this.logger.error("Error during compilation, see above for details");
					}
				}

				key.reset();
			}

		} catch (final IOException ex) {
			this.logger.error("Problem on Watch Service", ex);
		}
	}

	private void registerFolderAndSubFolders(final WatchService watcher, final Path directory) throws IOException {

		directory.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);

		this.logger.info("Watch Service registered for dir: " + directory.getFileName());

		for (final File subFile : directory.toFile().listFiles()) {
			if (subFile.isDirectory()) {
				this.registerFolderAndSubFolders(watcher, subFile.toPath());
			}
		}
	}

	/**
	 * Check and build watched directory path
	 *
	 * @param compileLibSassTaskDelegate
	 * @return
	 */
	private Path getWatchedDirectory(final Project project) {
		PluginParameters extension = project.getExtensions().findByType(PluginParameters.class);
		if (extension == null) {
			extension = new PluginParameters();
		}
		final String watchedDirectoryPath = extension.getWatchedDirectoryPath();

		final Path watchedDirectory = Paths.get(project.getRootDir().getAbsolutePath(), watchedDirectoryPath);

		if (watchedDirectory == null) {
			throw new IllegalArgumentException("watchedDirectoryPath must be set");
		}
		final File file = watchedDirectory.toFile();
		if (!file.exists()) {
			throw new IllegalArgumentException(watchedDirectory.toAbsolutePath().toString() + " doesn't exist");
		}

		if (!file.isDirectory()) {
			throw new IllegalArgumentException(watchedDirectory.toAbsolutePath().toString() + " should be a folder");
		}
		return watchedDirectory;
	}

}
