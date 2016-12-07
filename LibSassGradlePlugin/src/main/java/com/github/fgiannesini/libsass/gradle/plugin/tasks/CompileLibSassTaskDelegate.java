package com.github.fgiannesini.libsass.gradle.plugin.tasks;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import com.github.fgiannesini.libsass.gradle.plugin.compiler.LibSassCompiler;
import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginMode;
import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginParametersProvider;

import io.bit3.jsass.CompilationException;

/**
 * Delegate to initialisation and call to libsass library
 *
 */
public class CompileLibSassTaskDelegate {

    /**
     * Lib sass compiler
     */
    private final LibSassCompiler libSassCompiler;

    public CompileLibSassTaskDelegate(final Project project,
            final Logger logger) {
        final PluginParametersProvider parametersProvider = new PluginParametersProvider(
                project, PluginMode.PRODUCTION);

        this.libSassCompiler = new LibSassCompiler(logger, parametersProvider);
    }

    /**
     * Launche libsass compilation
     *
     * @throws CompilationException
     */
    public void compile() throws CompilationException {
        this.libSassCompiler.compileFile();
    }
}
