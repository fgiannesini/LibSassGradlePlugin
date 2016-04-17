package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import io.bit3.jsass.CompilationException;

/**
 * Continuous compilation by watching files modifications in a directory
 */
public class CompileLibSassWithWatchTask extends DefaultTask {
    @Override
    public String getDescription() {
        return "Continous compilation of sass/scss files to css (with optional source map) with LibSass";
    }

    @TaskAction
    public void compileLibSass() throws CompilationException {
        final CompileLibSassWatcher watcher = new CompileLibSassWatcher(
                this.getLogger(), this.getProject());
        watcher.launchWatcher();
    }
}
