package com.github.fgiannesini.libsass.gradle.plugin.compiler;

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

	/**
	 * LibSass options
	 */
	private final Options options;

	/**
	 * Gradle logger
	 */
	private final Logger logger;

	public LibSassCompiler(final Logger logger) {
		this.options = new Options();
		this.logger = logger;
	}

	/**
	 * Compile an input file to an output file (and optionnaly a map file).
	 * Files path are build if necessary
	 *
	 * @param inputFile
	 * @param outputFile
	 * @throws CompilationException
	 */
	public void compileFile(final File inputFile, final File outputFile) throws CompilationException {

		final URI inputFileURI = inputFile.toURI();
		final URI outputFileURI = outputFile.toURI();

		final io.bit3.jsass.Compiler compiler = new io.bit3.jsass.Compiler();

		if (this.logger.isInfoEnabled()) {
			this.logger.info("Launching compilation");
		}

		final Output output;

		// jsass version 5, errors from exception not displayed
		// try {
		output = compiler.compileFile(inputFileURI, outputFileURI, this.options);
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
				final File sourceMapFile = new File(this.options.getSourceMapFile());
				this.logger.info("Write source map file " + sourceMapFile.getPath());
				FileUtils.forceMkdir(sourceMapFile.getParentFile());
				FileUtils.write(sourceMapFile, sourceMap);
			} catch (final IOException e) {
				this.logger.error("Error writing source map file", e);
			}
		}
	}

	/**
	 * Default: nested Values: nested, expanded, compact, compressed Determines
	 * the output format of the final CSS style.
	 *
	 * @param outputStyle
	 */
	public void setOutputStyle(final PluginOutputStyle outputStyle) {
		this.options.setOutputStyle(OutputStyle.values()[outputStyle.ordinal()]);
	}

	/**
	 * Default: "" Paths that LibSass can look in to attempt to resolve your
	 * declarations. When using data, it is recommended that you use this. ';'
	 * is the path separator for Windows ':' is the path separator for Linux
	 *
	 * @param includePaths
	 */
	public void addIncludePaths(final String... includePaths) {
		Stream<File> files = Arrays.stream(includePaths).map(File::new);
		if (this.logger.isInfoEnabled()) {
			files = files.peek(file -> this.logger.info(file.getPath()));
		}
		files.forEach(this.options.getIncludePaths()::add);
	}

	/**
	 * Default: false. 'true' enables additional debugging information in the
	 * output file as CSS comments
	 *
	 * @param generateSourceComments
	 */
	public void setSourceComments(final boolean generateSourceComments) {
		this.options.setSourceComments(generateSourceComments);
	}

	/**
	 * Default: false 'true' values disable the inclusion of source map
	 * information in the output file
	 *
	 * @param omitSourceMappingURL
	 */
	public void setOmitSourceMappingURL(final boolean omitSourceMappingURL) {
		this.options.setOmitSourceMapUrl(omitSourceMappingURL);
	}

	/**
	 * Default: false 'true' embeds the source map as a data URI
	 *
	 * @param embedSourceMapInCSS
	 */
	public void setSourceMapEmbed(final boolean embedSourceMapInCSS) {
		this.options.setSourceMapEmbed(embedSourceMapInCSS);
	}

	/**
	 * Default: false 'true' includes the contents in the source map information
	 *
	 * @param embedSourceContentsInSourceMap
	 */
	public void setSourceMapContents(final boolean embedSourceContentsInSourceMap) {
		this.options.setSourceMapContents(embedSourceContentsInSourceMap);
	}

	/**
	 * Default: 'scss' Input syntax 'scss' or 'sass'
	 *
	 * @param inputSyntax
	 */
	public void setInputSyntax(final PluginInputSyntax inputSyntax) {
		this.options.setIsIndentedSyntaxSrc(inputSyntax == PluginInputSyntax.SASS);
	}

	/**
	 * Default: 5 Used to determine how many digits after the decimal will be
	 * allowed. For instance, if you had a decimal number
	 *
	 * @param precision
	 */
	public void setPrecision(final int precision) {
		this.options.setPrecision(precision);
	}

	/**
	 * Path of source file to generate if not embedded
	 *
	 * @param sourceMapFile
	 */
	public void setSourceMapFile(final File sourceMapFile) {
		this.options.setSourceMapFile(sourceMapFile.toURI());
	}
}