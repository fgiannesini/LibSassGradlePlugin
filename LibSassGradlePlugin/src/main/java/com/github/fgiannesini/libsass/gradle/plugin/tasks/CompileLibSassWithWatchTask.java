package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;

import io.bit3.jsass.CompilationException;

public class CompileLibSassWithWatchTask extends DefaultTask {
    @Override
    public String getDescription() {
        return "Continous compilation of sass/scss files to css (with optional source map) with LibSass";
    }

    @TaskAction
    public void compileLibSass() throws CompilationException {
        final Logger logger = this.getLogger();
        final CompileLibSassTaskDelegate compileLibSassTaskDelegate = new CompileLibSassTaskDelegate(
                this.getProject(), logger);

        final Path watchedDirectory = this
                .getWatchedDirectory(compileLibSassTaskDelegate);

        try {
            final WatchService watcher = FileSystems.getDefault()
                    .newWatchService();

            watchedDirectory.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            logger.info("Watch Service registered for dir: "
                    + watchedDirectory.getFileName());

            while (true) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (final InterruptedException ex) {
                    logger.error("Wath Serice interrupted", ex);
                    return;
                }

                for (final WatchEvent<?> event : key.pollEvents()) {
                    final WatchEvent.Kind<?> kind = event.kind();

                    @SuppressWarnings("unchecked")
                    final WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    final Path fileName = ev.context();

                    logger.info(kind.name() + ": " + fileName);

                    compileLibSassTaskDelegate.compile();
                }

                final boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (final IOException ex) {
            logger.error("Problem on Watch Service", ex);
        }
    }

    private Path getWatchedDirectory(
            final CompileLibSassTaskDelegate compileLibSassTaskDelegate) {
        final Path watchedDirectory = compileLibSassTaskDelegate
                .getWatchedDirectoryPath();
        if (watchedDirectory == null) {
            throw new IllegalArgumentException(
                    "watchedDirectoryPath must be set");
        }
        if (!watchedDirectory.toFile().exists()) {
            throw new IllegalArgumentException(
                    watchedDirectory.toAbsolutePath().toString()
                            + "doesn't exist");
        }
        return watchedDirectory;
    }
}
