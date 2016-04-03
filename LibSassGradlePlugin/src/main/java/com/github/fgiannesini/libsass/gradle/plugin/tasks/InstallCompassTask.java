package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import com.github.fgiannesini.libsass.gradle.plugin.extension.LibSassParameters;
import com.github.fgiannesini.libsass.gradle.plugin.installer.CompassInstaller;
import com.github.fgiannesini.libsass.gradle.plugin.installer.FrameworkPropertiesEnum;
import com.github.fgiannesini.libsass.gradle.plugin.installer.ScssFrameworkInstaller;

/**
 * Task to install Bourbon sources to the project
 */
public class InstallCompassTask extends DefaultTask {

    @Override
    public String getDescription() {
        return "Install compass sources in defined directory. Old installation is not deleted.";
    }

    @TaskAction
    public void installCompass() throws Exception {
        final Project project = this.getProject();
        LibSassParameters extension = project.getExtensions()
                .findByType(LibSassParameters.class);
        if (extension == null) {
            extension = new LibSassParameters();
        }

        final ScssFrameworkInstaller scssFrameworkInstaller = new CompassInstaller(
                project, this.getLogger());

        scssFrameworkInstaller
                .setInstallationPath(extension.getCompassInstallationPath());
        scssFrameworkInstaller
                .setVersionToDownload(extension.getCompassVersion());

        scssFrameworkInstaller
                .installFramework(FrameworkPropertiesEnum.COMPASS);
    }

}
