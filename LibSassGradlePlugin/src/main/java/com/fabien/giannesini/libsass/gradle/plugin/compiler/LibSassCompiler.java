package com.fabien.giannesini.libsass.gradle.plugin.compiler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.gradle.api.logging.Logger;

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Options;
import io.bit3.jsass.Output;
import io.bit3.jsass.OutputStyle;

/**
 * LibSass compiler call
 */
public class LibSassCompiler {

    final Options options;

    private final Logger logger;

    public LibSassCompiler(final Logger logger) {
        this.options = new Options();
        this.logger = logger;
    }

    /**
     * All paths passed to this method must be relative to the same directory.
     */
    public void compileFile(final File inputFile, final File outputFile)
            throws CompilationException {

        final URI inputFileURI = inputFile.toURI();
        final URI outputFileURI = outputFile.toURI();

        final io.bit3.jsass.Compiler compiler = new io.bit3.jsass.Compiler();

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Launching compilation");
        }

        final Output output;

        // jsass version 5, errors from exception not displayed
        // try {
        output = compiler.compileFile(inputFileURI, outputFileURI,
                this.options);
        // } catch (final CompilationException compilationException) {
        // this.logger.error(compilationException.getErrorFile());
        // this.logger.error(compilationException.getErrorJson());
        // this.logger.error(compilationException.getErrorMessage());
        // this.logger.error(compilationException.getErrorSrc());
        // this.logger.error(compilationException.getErrorText());
        // throw compilationException;
        // }
        if (output.getErrorStatus() != 0) {
            this.logger.error(output.getErrorFile());
            this.logger.error(output.getErrorJson());
            this.logger.error(output.getErrorMessage());
            this.logger.error(output.getErrorSrc());
            this.logger.error(output.getErrorText());
        }
        try {
            this.logger.info("Write CSS file " + outputFile.getPath());
            FileUtils.forceMkdir(outputFile.getParentFile());
            FileUtils.write(outputFile, output.getCss());
        } catch (final IOException e) {
            this.logger.error("Error writing CSS file", e);
        }

        final String sourceMap = output.getSourceMap();
        if (sourceMap != null) {
            try {
                final File sourceMapFile = new File(
                        this.options.getSourceMapFile());
                this.logger.info(
                        "Write source map file " + sourceMapFile.getPath());
                FileUtils.forceMkdir(sourceMapFile.getParentFile());
                FileUtils.write(sourceMapFile, sourceMap);
            } catch (final IOException e) {
                this.logger.error("Error writing source map file", e);
            }
        }
    }

    public void setOutputStyle(final PluginOutputStyle outputStyle) {
        this.options
                .setOutputStyle(OutputStyle.values()[outputStyle.ordinal()]);
    }

    public void addIncludePaths(final String... includePaths) {
        Stream<File> files = Arrays.stream(includePaths).map(File::new);
        if (this.logger.isInfoEnabled()) {
            files = files.peek(file -> this.logger.info(file.getPath()));
        }
        files.forEach(this.options.getIncludePaths()::add);
    }

    public void setSourceComments(final boolean generateSourceComments) {
        this.options.setSourceComments(generateSourceComments);
    }

    public void setOmitSourceMappingURL(final boolean omitSourceMappingURL) {
        this.options.setOmitSourceMapUrl(omitSourceMappingURL);
    }

    public void setSourceMapEmbed(final boolean embedSourceMapInCSS) {
        this.options.setSourceMapEmbed(embedSourceMapInCSS);
    }

    public void setSourceMapContents(
            final boolean embedSourceContentsInSourceMap) {
        this.options.setSourceMapContents(embedSourceContentsInSourceMap);
    }

    public void setInputSyntax(final PluginInputSyntax inputSyntax) {
        this.options
                .setIsIndentedSyntaxSrc(inputSyntax == PluginInputSyntax.SASS);
    }

    public void setPrecision(final int precision) {
        this.options.setPrecision(precision);
    }

    public void setSourceMapFile(final File sourceMapFile) {
        this.options.setSourceMapFile(sourceMapFile.toURI());
    }
}