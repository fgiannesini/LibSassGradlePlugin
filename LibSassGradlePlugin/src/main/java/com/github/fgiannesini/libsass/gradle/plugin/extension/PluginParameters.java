package com.github.fgiannesini.libsass.gradle.plugin.extension;

/**
 * Gradle plugin parameters declaration for development tasks
 */
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

    public String getWatchedDirectoryPath() {
        return this.watchedDirectoryPath;
    }

    public void setWatchedDirectoryPath(final String watchedDirectoryPath) {
        this.watchedDirectoryPath = watchedDirectoryPath;
    }

    public String getBourbonInstallationPath() {
        return this.bourbonInstallationPath;
    }

    public void setBourbonInstallationPath(
            final String bourbonInstallationPath) {
        this.bourbonInstallationPath = bourbonInstallationPath;
    }

    public String getBourbonVersion() {
        return this.bourbonVersion;
    }

    public void setBourbonVersion(final String bourbonVersion) {
        this.bourbonVersion = bourbonVersion;
    }

    public String getCompassInstallationPath() {
        return this.compassInstallationPath;
    }

    public void setCompassInstallationPath(
            final String compassInstallationPath) {
        this.compassInstallationPath = compassInstallationPath;
    }

    public String getCompassVersion() {
        return this.compassVersion;
    }

    public void setCompassVersion(final String compassVersion) {
        this.compassVersion = compassVersion;
    }

}
