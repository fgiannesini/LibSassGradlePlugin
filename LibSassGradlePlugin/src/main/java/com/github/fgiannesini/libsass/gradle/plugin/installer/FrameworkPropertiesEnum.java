package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Framework properties
 */
public enum FrameworkPropertiesEnum {

    BOURBON("bourbon", Paths.get("app", "assets", "stylesheets"), "bourbon"),

    COMPASS("compass-core", Paths.get("stylesheets"), "compass");

    /**
     * Gem name for download
     */
    private String gemName;

    /**
     * Internal path in gem to access main sources directory
     */
    private Path internalGemSourcesPath;

    /**
     * Name of installation folder
     */
    private String folderInstallationName;

    private FrameworkPropertiesEnum(final String gemName,
            final Path internalGemSourcesPath,
            final String folderInstallationName) {
        this.gemName = gemName;
        this.internalGemSourcesPath = internalGemSourcesPath;
        this.folderInstallationName = folderInstallationName;
    }

    public String getGemName() {
        return this.gemName;
    }

    public Path getInternalGemSourcesPath() {
        return this.internalGemSourcesPath;
    }

    public String getFolderInstallationName() {
        return this.folderInstallationName;
    }

}
