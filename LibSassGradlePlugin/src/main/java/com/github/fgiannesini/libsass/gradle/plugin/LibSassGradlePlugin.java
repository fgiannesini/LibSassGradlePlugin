package com.github.fgiannesini.libsass.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginParameters;
import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginProductionParameters;
import com.github.fgiannesini.libsass.gradle.plugin.tasks.CompileLibSassProductionTask;
import com.github.fgiannesini.libsass.gradle.plugin.tasks.CompileLibSassTask;
import com.github.fgiannesini.libsass.gradle.plugin.tasks.CompileLibSassWithWatchTask;
import com.github.fgiannesini.libsass.gradle.plugin.tasks.InstallBourbonTask;
import com.github.fgiannesini.libsass.gradle.plugin.tasks.InstallCompassTask;

/**
 * Gradle plugin declaration
 */
public class LibSassGradlePlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        // Class for definition of availables parameters
        final ExtensionContainer extensions = project.getExtensions();
        final PluginParameters libSassParameters = extensions
                .create("libSassParameters", PluginParameters.class);
        ((ExtensionAware) libSassParameters).getExtensions().create(
                "libSassProductionParameters",
                PluginProductionParameters.class);

        final TaskContainer tasks = project.getTasks();

        // Task for libsass compilation with development configuration
        tasks.create("compileLibSass", CompileLibSassTask.class);

        // Task for libsass compilation with production configuration
        tasks.create("compileLibSassProdution",
                CompileLibSassProductionTask.class);

        // Task for continous compilation
        tasks.create("compileLibSassWithWatch",
                CompileLibSassWithWatchTask.class);

        // Task to install Bourbon sources
        tasks.create("installBourbon", InstallBourbonTask.class);

        // Task to install Compass sources
        tasks.create("installCompass", InstallCompassTask.class);

    }

}
