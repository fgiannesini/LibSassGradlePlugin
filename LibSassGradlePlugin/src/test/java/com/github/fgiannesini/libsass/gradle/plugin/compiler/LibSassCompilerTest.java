package com.github.fgiannesini.libsass.gradle.plugin.compiler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.gradle.api.logging.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.Assert;

import com.github.fgiannesini.libsass.gradle.plugin.extension.PluginParametersProvider;

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Output;

@RunWith(MockitoJUnitRunner.class)
public class LibSassCompilerTest {

	@Mock
	private Logger logger;

	@Mock
	private PluginParametersProvider parametersProvider;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void initPluginParameterProvider() throws IOException {
		Mockito.when(this.parametersProvider.getIncludePaths())
				.thenReturn(new String[] { this.folder.newFile().getAbsolutePath() });
		Mockito.when(this.parametersProvider.getInputSyntax()).thenReturn(PluginInputSyntax.SCSS);
		Mockito.when(this.parametersProvider.getOmitSourceMappingUrl()).thenReturn(Boolean.FALSE);
		Mockito.when(this.parametersProvider.getOutputStyle()).thenReturn(PluginOutputStyle.NESTED);
		Mockito.when(this.parametersProvider.getPrecision()).thenReturn(Integer.valueOf(5));
		Mockito.when(this.parametersProvider.getSourceComments()).thenReturn(Boolean.FALSE);
		Mockito.when(this.parametersProvider.getSourceMapContents()).thenReturn(Boolean.FALSE);
		Mockito.when(this.parametersProvider.getSourceMapEmbed()).thenReturn(Boolean.FALSE);

		Mockito.when(this.parametersProvider.getOutputUri()).thenReturn(this.folder.newFile().toURI());
		Mockito.when(this.parametersProvider.getInputUri()).thenReturn(this.folder.newFile().toURI());
	}

	@Test
	public void launchCompilation_Empty_File() throws IOException, CompilationException {
		// Prepare
		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);

		// Call
		final Output output = compiler.launchCompilation();

		// Test
		Assert.assertTrue(output.getCss() != null);
		Assert.assertTrue(StringUtils.isBlank(output.getCss()));
		Mockito.verify(this.logger, Mockito.never()).error(Matchers.anyString());
	}

	@Test
	public void launchCompilation_Minimal_Css() throws IOException, CompilationException {
		// Prepare
		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);

		final File inputFile = this.folder.newFile("input");
		final String cssText = "body{text-align:left;}";
		FileUtils.write(inputFile, cssText);

		Mockito.when(this.parametersProvider.getInputUri()).thenReturn(inputFile.toURI());

		// Call
		final Output output = compiler.launchCompilation();

		// Test
		Assert.assertTrue(StringUtils.isNotBlank(output.getCss()));
		Mockito.verify(this.logger, Mockito.never()).error(Matchers.anyString());
	}

	@Test(expected = CompilationException.class)
	public void launchCompilation_Incorrect_Css() throws IOException, CompilationException {
		// Prepare
		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);

		final File inputFile = this.folder.newFile("input");
		final String cssText = "body{";
		FileUtils.write(inputFile, cssText);
		Mockito.when(this.parametersProvider.getInputUri()).thenReturn(inputFile.toURI());

		// Call
		final Output output = compiler.launchCompilation();

		// Test
		Assert.assertTrue(StringUtils.isBlank(output.getCss()));
		Mockito.verify(this.logger, Mockito.times(5)).error(Matchers.anyString());
	}

	@Test
	public void generateSourceMap_Generation() throws IOException {
		// Prepare
		final File sourceMapFile = this.folder.newFile();
		Mockito.when(this.parametersProvider.getSourceMapUri()).thenReturn(sourceMapFile.toURI());

		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);
		final Output output = new Output(null, "sourceMap");

		// Call
		compiler.generateSourceMap(output);

		// Check
		Assert.assertTrue(StringUtils.isNotBlank(FileUtils.readFileToString(sourceMapFile)));
		Mockito.verify(this.logger, Mockito.never()).error(Matchers.anyString(), Matchers.any(IOException.class));
	}

	@Test
	public void generateSourceMap_No_SourceMap() throws IOException {
		// Prepare
		final File sourceMapFile = this.folder.newFile();
		Mockito.when(this.parametersProvider.getSourceMapUri()).thenReturn(sourceMapFile.toURI());

		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);
		final Output output = new Output(null, null);

		// Call
		compiler.generateSourceMap(output);

		// Check
		Assert.assertTrue(StringUtils.isBlank(FileUtils.readFileToString(sourceMapFile)));
		Mockito.verify(this.logger, Mockito.never()).error(Matchers.anyString(), Matchers.any(IOException.class));
	}

	@Test
	public void generateSourceMap_IOException() throws IOException {
		// Prepare
		final File sourceMapFile = new File("");
		Mockito.when(this.parametersProvider.getSourceMapUri()).thenReturn(sourceMapFile.toURI());

		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);
		final Output output = new Output(null, "sourceMap");

		// Call
		compiler.generateSourceMap(output);

		// Check
		Mockito.verify(this.logger, Mockito.times(1)).error(Matchers.anyString(), Matchers.any(IOException.class));
	}

	@Test
	public void writeCss() throws IOException {
		// Prepare
		final File outputFile = this.folder.newFile();
		Mockito.when(this.parametersProvider.getOutputUri()).thenReturn(outputFile.toURI());

		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);
		final Output output = new Output("css", null);

		// Call
		compiler.writeCss(output);

		// Check
		Assert.assertTrue(StringUtils.isNotBlank(FileUtils.readFileToString(outputFile)));
		Mockito.verify(this.logger, Mockito.never()).error(Matchers.anyString(), Matchers.any(IOException.class));
	}

	@Test
	public void writeCss_IOException() throws IOException {
		// Prepare
		final File outputFile = new File(this.folder.getRoot().getAbsolutePath() + File.separator + "?");
		Mockito.when(this.parametersProvider.getOutputUri()).thenReturn(outputFile.toURI());

		final LibSassCompiler compiler = new LibSassCompiler(this.logger, this.parametersProvider);

		final Output output = new Output("css", null);

		// Call
		compiler.writeCss(output);

		// Check
		Mockito.verify(this.logger, Mockito.times(1)).error(Matchers.anyString(), Matchers.any(IOException.class));
	}
}
