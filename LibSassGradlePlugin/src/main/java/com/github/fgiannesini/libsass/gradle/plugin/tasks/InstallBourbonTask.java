package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;

import com.github.fgiannesini.libsass.gradle.plugin.extension.LibSassParameters;
import com.github.fgiannesini.libsass.gradle.plugin.jruby.JRubyCaller;

/**
 * Task to install Bourbon sources to the project
 */
public class InstallBourbonTask extends DefaultTask {

    @Override
    public String getDescription() {
        return "Install bourbon sources in defined directory. Old installation is not deleted.";
    }

    @TaskAction
    public void installBoubon() throws Exception {
        LibSassParameters extension = this.getProject().getExtensions()
                .findByType(LibSassParameters.class);
        if (extension == null) {
            extension = new LibSassParameters();
        }

        final Logger logger = this.getLogger();
        final JRubyCaller jrubyCaller = new JRubyCaller(logger);
        final String gemName = "bourbon";
        final File folder = jrubyCaller.unpack(gemName,
                extension.getBourbonVersion());
        try {
            final File sourcesFolder = this.getSourcesFolder(logger, gemName,
                    folder);

            final Path installationPath = this.getInstallationPath(extension,
                    logger);
            this.copySourcesToInstallationPath(logger, sourcesFolder,
                    installationPath);
        } finally {
            FileUtils.deleteDirectory(folder);
        }
    }

    private void copySourcesToInstallationPath(final Logger logger,
            final File sourcesFolder, final Path installationPath)
            throws IOException {
        for (final File source : sourcesFolder.listFiles()) {
            if (source.isDirectory()) {
                FileUtils.copyDirectory(source,
                        installationPath.resolve(source.getName()).toFile());
                logger.info("Folder " + source.getName() + " copied");
            } else {
                FileUtils.copyFile(source,
                        installationPath.resolve(source.getName()).toFile());
                logger.info("File " + source.getName() + " copied");
            }
        }
    }

    /**
     * Retrieve from parameters install path and build missing directories if
     * missing
     *
     * @param extension
     * @param logger
     * @return
     * @throws IOException
     */
    private Path getInstallationPath(final LibSassParameters extension,
            final Logger logger) throws IOException {
        final Path installPath = Paths.get(
                this.getProject().getRootDir().getAbsolutePath(),
                extension.getBourbonInstallationPath(), "bourbon");
        FileUtils.forceMkdir(installPath.toFile());
        logger.info("Install folder : " + installPath);
        return installPath;
    }

    /**
     * Retrieve folder containing scss sources from unpacked gem
     *
     * @param logger
     * @param gemName
     * @param folder
     * @return
     * @throws Exception
     */
    private File getSourcesFolder(final Logger logger, final String gemName,
            final File folder) throws Exception {
        this.checkFolder(logger, gemName, folder);
        final File sourcesFolder = Paths
                .get(folder.listFiles()[0].getAbsolutePath(), "app", "assets",
                        "stylesheets")
                .toFile();
        logger.info(gemName + " sources folder : "
                + sourcesFolder.getAbsolutePath());
        return sourcesFolder;
    }

    /**
     * Check that unpack folder is correct, ie contains only one directory
     *
     * @param logger
     * @param gemName
     * @param folder
     * @throws Exception
     */
    private void checkFolder(final Logger logger, final String gemName,
            final File folder) throws Exception {
        final File[] unpackedFiles = folder.listFiles();
        if (unpackedFiles.length != 1) {
            logger.error("Folder " + folder.getAbsolutePath()
                    + "should contains only one folder. Folder content :");
            Arrays.stream(unpackedFiles).map(File::getName)
                    .forEach(logger::error);
            throw new Exception("A problem occured while retrieving " + gemName
                    + "sources files");
        }
    }
}
