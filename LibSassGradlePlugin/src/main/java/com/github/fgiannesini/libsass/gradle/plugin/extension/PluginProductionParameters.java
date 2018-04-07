package com.github.fgiannesini.libsass.gradle.plugin.extension;

/**
 * Parameters for production tasks
 */
public class PluginProductionParameters {

    /**
     * File path to compile (normally import other scss files)
     */
    private String inputFilePath;

    /**
     * Compiled file path
     */
    private String outputFilePath;

    /**
     * Path of source file to generate if not embedded
     */
    private String sourceMapFilePath;
    /**
     * Default: "" Paths that LibSass can look in to attempt to resolve your
     * declarations. When using data, it is recommended that you use this. ';'
     * is the path separator for Windows ':' is the path separator for Linux
     */
    private String includePaths;
    /**
     * Default: nested Values: nested, expanded, compact, compressed Determines
     * the output format of the final CSS style.
     */
    private String outputStyle;
    /**
     * Default: false. 'true' enables additional debugging information in the
     * output file as CSS comments
     */
    private Boolean sourceComments;
    /**
     * Default: false 'true' values disable the inclusion of source map
     * information in the output file
     */
    private Boolean omitSourceMappingURL;
    /**
     * Default: false 'true' embeds the source map as a data URI
     */
    private Boolean sourceMapEmbed;
    /**
     * Default: false 'true' includes the contents in the source map information
     */
    private Boolean sourceMapContents;
    /**
     * Default: 'scss' Input syntax 'scss' or 'sass'
     */
    private String inputSyntax;
    /**
     * * Default: 5 Used to determine how many digits after the decimal will be
     * allowed. For instance, if you had a decimal number
     */
    private Integer precision;

    public String getInputFilePath() {
        return this.inputFilePath;
    }

    public void setInputFilePath(final String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return this.outputFilePath;
    }

    public void setOutputFilePath(final String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public String getSourceMapFilePath() {
        return this.sourceMapFilePath;
    }

    public void setSourceMapFilePath(final String sourceMapFilePath) {
        this.sourceMapFilePath = sourceMapFilePath;
    }

    public String getIncludePaths() {
        return this.includePaths;
    }

    public void setIncludePaths(final String includePaths) {
        this.includePaths = includePaths;
    }

    public String getOutputStyle() {
        return this.outputStyle;
    }

    public void setOutputStyle(final String outputStyle) {
        this.outputStyle = outputStyle;
    }

    public Boolean getSourceComments() {
        return this.sourceComments;
    }

    public void setSourceComments(final Boolean sourceComments) {
        this.sourceComments = sourceComments;
    }

    public Boolean getOmitSourceMappingURL() {
        return this.omitSourceMappingURL;
    }

    public void setOmitSourceMappingURL(final Boolean omitSourceMappingURL) {
        this.omitSourceMappingURL = omitSourceMappingURL;
    }

    public Boolean getSourceMapEmbed() {
        return this.sourceMapEmbed;
    }

    public void setSourceMapEmbed(final Boolean sourceMapEmbed) {
        this.sourceMapEmbed = sourceMapEmbed;
    }

    public Boolean getSourceMapContents() {
        return this.sourceMapContents;
    }

    public void setSourceMapContents(final Boolean sourceMapContents) {
        this.sourceMapContents = sourceMapContents;
    }

    public String getInputSyntax() {
        return this.inputSyntax;
    }

    public void setInputSyntax(final String inputSyntax) {
        this.inputSyntax = inputSyntax;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(final Integer precision) {
        this.precision = precision;
    }

}
