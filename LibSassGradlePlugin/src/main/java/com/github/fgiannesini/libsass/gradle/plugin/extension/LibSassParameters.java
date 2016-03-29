package com.github.fgiannesini.libsass.gradle.plugin.extension;

import lombok.Getter;
import lombok.Setter;

/**
 * Gradle plugin parameters declaration
 */
@Getter
@Setter
public class LibSassParameters {

    private String inputFilePath;
    private String watchedDirectoryPath;

    private String outputFilePath;

    private String sourceMapFilePath;
    private String includePaths;
    private String outputStyle;
    private Boolean sourceComments;
    private Boolean omitSourceMappingURL;
    private Boolean sourceMapEmbed;
    private Boolean sourceMapContents;
    private String inputSyntax;
    private Integer precision;
}
