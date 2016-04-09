package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

/**
 * Specific installer for Compass
 */
public class CompassInstaller extends ScssFrameworkInstaller {

    public CompassInstaller(final Project project, final Logger logger) {
        super(project, logger);
    }

    @Override
    protected void copySourcesToInstallationPath(final File sourcesFolder,
            final Path installationPath) throws IOException {
        this.correctFilesImports(sourcesFolder, 0);
        super.copySourcesToInstallationPath(sourcesFolder, installationPath);
    }

    /**
     * Correct compass files for libsass compilation. <br>
     * <br>
     *
     *
     * This method deals with files recursively
     *
     * @param sourcesFolder
     * @param level
     *            Folder depth in recursion
     * @throws IOException
     */
    protected void correctFilesImports(final File sourcesFolder,
            final int level) throws IOException {
        final File[] listFiles = sourcesFolder.listFiles();
        if (listFiles == null) {
            return;
        }
        for (final File file : listFiles) {
            if (file.isDirectory()) {
                this.correctFilesImports(file, level + 1);
            } else if (file.getName().endsWith(".scss")) {
                this.correctFileImport(level, file);
            }
        }
    }

    /**
     * Correct import in compass File <br>
     * <br>
     * {@code @import
     * "compass/support"} in file <i>compass/css3/_animation.scss</i> should
     * become {@code @import "../../compass/support"} <br>
     * <br>
     *
     * @param level
     * @param file
     * @throws IOException
     */
    private void correctFileImport(final int level, final File file)
            throws IOException {
        this.logger
                .info("Correcting imports in compass file " + file.getName());
        final List<String> lines = FileUtils.readLines(file);
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            if (line.trim().startsWith("@import")) {
                final String originFolderPath = StringUtils.repeat("../", level)
                        + "compass/";
                lines.set(i, line.replaceAll("compass/", originFolderPath));
            }
        }
        FileUtils.writeLines(file, lines);
    }
}
