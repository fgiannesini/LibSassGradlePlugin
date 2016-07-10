package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginMode;
import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginParametersProvider;
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

		final PluginParametersProvider parametersProvider = new PluginParametersProvider(project,
				PluginMode.DEVELOPPEMENT);

		final ScssFrameworkInstaller scssFrameworkInstaller = new CompassInstaller(project, this.getLogger());

		scssFrameworkInstaller.setInstallationPath(parametersProvider.getCompassInstallationPath());
		scssFrameworkInstaller.setVersionToDownload(parametersProvider.getCompassVersion());

		scssFrameworkInstaller.installFramework(FrameworkPropertiesEnum.COMPASS);
	}

}
