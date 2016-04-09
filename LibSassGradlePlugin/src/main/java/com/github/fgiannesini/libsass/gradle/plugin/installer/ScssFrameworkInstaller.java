package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import com.github.fgiannesini.libsass.gradle.plugin.installer.jruby.JRubyCaller;

import lombok.Setter;

/**
 * Library installer for scss framework using JRuby
 *
 */
@Setter
public class ScssFrameworkInstaller {

    /**
     * Specific version to download
     */
    private String versionToDownload;

    /**
     * Path to install sources
     */
    private String installationPath;

    /**
     * Current gradle project
     */
    private final Project project;

    /**
     * Gradle logger
     */
    protected final Logger logger;

    public ScssFrameworkInstaller(final Project project, final Logger logger) {
        this.project = project;
        this.logger = logger;
    }

    /**
     * Launch framework installation
     *
     * @param frameworkProperties
     * @throws Exception
     */
    public void installFramework(
            final FrameworkPropertiesEnum frameworkProperties)
            throws Exception {
        final File folder = this.downloadSources(frameworkProperties);
        this.installSources(frameworkProperties, folder);
    }

    private void installSources(
            final FrameworkPropertiesEnum frameworkProperties,
            final File folder) throws Exception {
        try {
            final File sourcesFolder = this
                    .getSourcesFolder(frameworkProperties, folder);

            final Path installationPath = this.getInstallationPath(
                    frameworkProperties.getFolderInstallationName());
            this.copySourcesToInstallationPath(sourcesFolder, installationPath);
        } finally {
            FileUtils.deleteDirectory(folder);
        }
    }

    private File downloadSources(
            final FrameworkPropertiesEnum frameworkProperties)
            throws IOException {
        final JRubyCaller jrubyCaller = new JRubyCaller(this.logger);
        final File folder = jrubyCaller.unpack(frameworkProperties.getGemName(),
                this.versionToDownload);
        return folder;
    }

    protected void copySourcesToInstallationPath(final File sourcesFolder,
            final Path installationPath) throws IOException {
        final File[] listFiles = sourcesFolder.listFiles();
        if (listFiles == null) {
            this.logger.error(
                    "No files in folder " + sourcesFolder.getAbsolutePath());
            return;
        }
        for (final File source : listFiles) {
            if (source.isDirectory()) {
                FileUtils.copyDirectory(source,
                        installationPath.resolve(source.getName()).toFile());
                this.logger.info("Folder " + source.getName() + " copied");
            } else {
                FileUtils.copyFile(source,
                        installationPath.resolve(source.getName()).toFile());
                this.logger.info("File " + source.getName() + " copied");
            }
        }
    }

    /**
     * Retrieve from parameters install path and build missing directories if
     * missing
     *
     * @param folderInstallationName
     *
     * @return
     * @throws IOException
     */
    private Path getInstallationPath(final String folderInstallationName)
            throws IOException {
        if (this.installationPath == null) {
            throw new IOException(
                    "Installation path is a mandatory parameter, please set it");
        }

        final Path installPath = Paths.get(
                this.project.getRootDir().getAbsolutePath(),
                this.installationPath, folderInstallationName);
        FileUtils.forceMkdir(installPath.toFile());
        this.logger.info("Install folder : " + installPath);
        return installPath;
    }

    /**
     * Retrieve folder containing scss sources from unpacked gem
     *
     * @param frameworkProperties
     * @param folder
     *
     * @return
     * @throws Exception
     */
    private File getSourcesFolder(
            final FrameworkPropertiesEnum frameworkProperties,
            final File folder) throws Exception {
        this.checkFolder(frameworkProperties.getGemName(), folder);
        final File[] listFiles = folder.listFiles();
        if (listFiles == null) {
            return null;
        }
        final File sourcesFolder = Paths.get(listFiles[0].getAbsolutePath())
                .resolve(frameworkProperties.getInternalGemSourcesPath())
                .toFile();
        this.logger.info(frameworkProperties + " sources folder : "
                + sourcesFolder.getAbsolutePath());
        return sourcesFolder;
    }

    /**
     * Check that unpack folder is correct, ie contains only one directory
     *
     * @param gemName
     * @param folder
     *
     * @throws Exception
     */
    private void checkFolder(final String gemName, final File folder)
            throws Exception {
        final File[] unpackedFiles = folder.listFiles();
        if (unpackedFiles == null) {
            this.logger.error("No files in folder " + folder.getAbsolutePath());
            return;
        }
        if (unpackedFiles.length != 1) {
            this.logger.error("Folder " + folder.getAbsolutePath()
                    + "should contains only one folder. Folder content :");
            Arrays.stream(unpackedFiles).map(File::getName)
                    .forEach(this.logger::error);
            throw new Exception("A problem occured while retrieving " + gemName
                    + "sources files");
        }
    }
}
