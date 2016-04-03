package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Framework properties
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum FrameworkPropertiesEnum {

    BOURBON("bourbon", Paths.get("app", "assets", "stylesheets"), "bouron"),

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
}
