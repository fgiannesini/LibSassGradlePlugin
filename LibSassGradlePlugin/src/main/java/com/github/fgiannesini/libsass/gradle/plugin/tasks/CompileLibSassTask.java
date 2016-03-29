package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import io.bit3.jsass.CompilationException;

/**
 * LibSass compilation task call
 */
public class CompileLibSassTask extends DefaultTask {

    @Override
    public String getDescription() {
        return "Compile sass/scss files to css (with optional source map) with LibSass";
    }

    @TaskAction
    public void compileLibSass() throws CompilationException {
        final CompileLibSassTaskDelegate compileLibSassTaskDelegate = new CompileLibSassTaskDelegate(
                this.getProject(), this.getLogger());
        compileLibSassTaskDelegate.compile();
    }
}
