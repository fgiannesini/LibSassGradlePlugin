package com.github.fgiannesini.libsass.gradle.plugin.extension;

import lombok.Getter;
import lombok.Setter;

/**
 * Gradle plugin parameters declaration for development tasks
 */
@Getter
@Setter
public class PluginParameters extends PluginProductionParameters {
	/**
	 * Directory to watch for continuous compilation
	 */
	private String watchedDirectoryPath;

	/**
	 * Installation folder for bourbon sources
	 */
	private String bourbonInstallationPath;

	/**
	 * Bourbon version needed
	 */
	private String bourbonVersion;

	/**
	 * Installation folder for compass sources
	 */
	private String compassInstallationPath;

	/**
	 * Compass version needed
	 */
	private String compassVersion;
}
