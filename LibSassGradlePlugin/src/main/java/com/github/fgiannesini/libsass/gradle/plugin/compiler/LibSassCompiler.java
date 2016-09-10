package com.github.fgiannesini.libsass.gradle.plugin.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.logging.Logger;

import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginParametersProvider;

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Options;
import io.bit3.jsass.Output;
import io.bit3.jsass.OutputStyle;

/**
 * LibSass compiler call
 */
public class LibSassCompiler {

	/**
	 * LibSass options
	 */
	private Options options;

	/**
	 * Gradle logger
	 */
	private final Logger logger;

	private final PluginParametersProvider parametersProvider;

	public LibSassCompiler(final Logger logger, final PluginParametersProvider parametersProvider) {
		this.logger = logger;
		this.parametersProvider = parametersProvider;
		this.init();
	}

	private void init() {
		this.options = new Options();

		this.options.setOutputStyle(OutputStyle.values()[this.parametersProvider.getOutputStyle().ordinal()]);
		Stream<File> files = Arrays.stream(this.parametersProvider.getIncludePaths()).map(File::new);
		if (this.logger.isInfoEnabled()) {
			files = files.peek(file -> this.logger.info(file.getPath()));
		}
		files.forEach(this.options.getIncludePaths()::add);
		this.options.setSourceComments(this.parametersProvider.getSourceComments());
		this.options.setOmitSourceMapUrl(this.parametersProvider.getOmitSourceMappingUrl());
		this.options.setSourceMapEmbed(this.parametersProvider.getSourceMapEmbed());
		this.options.setSourceMapContents(this.parametersProvider.getSourceMapContents());
		this.options.setIsIndentedSyntaxSrc(PluginInputSyntax.SASS.equals(this.parametersProvider.getInputSyntax()));
		this.options.setPrecision(this.parametersProvider.getPrecision());
		this.options.setSourceMapFile(this.parametersProvider.getSourceMapUri());
	}

	/**
	 * Compile an input file to an output file (and optionnaly a map file).
	 * Files path are built if necessary
	 *
	 * @throws CompilationException
	 */
	public void compileFile() throws CompilationException {

		final Output output = this.launchCompilation();
		this.writeCss(output);
		this.generateSourceMap(output);
	}

	protected void writeCss(final Output output) {
		try {
			final File outputFile = new File(this.parametersProvider.getOutputUri());
			this.logger.info("Write CSS file " + outputFile.getPath());
			FileUtils.forceMkdir(outputFile.getParentFile());
			FileUtils.write(outputFile, output.getCss(), Charset.defaultCharset());
		} catch (final IOException e) {
			this.logger.error("Error writing CSS file", e);
		}
	}

	protected void generateSourceMap(final Output output) {
		final String sourceMap = output.getSourceMap();
		if (StringUtils.isBlank(sourceMap)) {
			this.logger.info("No sourcemap file to generate");
			return;
		}
		try {
			final File sourceMapFile = new File(this.options.getSourceMapFile());
			this.logger.info("Write source map file " + sourceMapFile.getPath());
			FileUtils.forceMkdir(sourceMapFile.getParentFile());
			FileUtils.write(sourceMapFile, sourceMap, Charset.defaultCharset());
		} catch (final IOException e) {
			this.logger.error("Error writing source map file", e);
		}
	}

	protected Output launchCompilation() throws CompilationException {
		if (this.logger.isInfoEnabled()) {
			this.logger.info("Launching compilation");
		}
		final io.bit3.jsass.Compiler compiler = new io.bit3.jsass.Compiler();

		try {
			final Output output = compiler.compileFile(this.parametersProvider.getInputUri(),
					this.parametersProvider.getOutputUri(), this.options);

			if (this.logger.isInfoEnabled()) {
				this.logger.info("End of compilation");
			}
			return output;
		} catch (final CompilationException compilationException) {
			this.logger.error(compilationException.getErrorFile());
			this.logger.error(compilationException.getErrorJson());
			this.logger.error(compilationException.getErrorMessage());
			this.logger.error(compilationException.getErrorSrc());
			this.logger.error(compilationException.getErrorText());
			throw compilationException;
		}
	}

}