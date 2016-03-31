package com.github.fgiannesini.libsass.gradle.plugin.extension;

import lombok.Getter;
import lombok.Setter;

/**
 * Gradle plugin parameters declaration
 */
@Getter
@Setter
public class LibSassParameters {

	/**
	 * File path to compile (normally import other scss files)
	 */
	private String inputFilePath;
	/**
	 * Directory to watch for continuous compilation
	 */
	private String watchedDirectoryPath;

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
}
