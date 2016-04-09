package com.github.fgiannesini.libsass.gradle.plugin.compiler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.gradle.api.logging.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.Assert;

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Output;

@RunWith(MockitoJUnitRunner.class)
public class LibSassCompilerTest {

    @Mock
    private Logger logger;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void launchCompilation_Empty_File()
            throws IOException, CompilationException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);

        // Call
        final Output output = compiler.launchCompilation(
                this.folder.newFile("input").toURI(),
                this.folder.newFile("output").toURI());

        // Test
        Assert.assertTrue(output.getCss() != null);
        Assert.assertTrue(StringUtils.isBlank(output.getCss()));
        Mockito.verify(this.logger, Mockito.never()).error(Mockito.anyString());
    }

    @Test
    public void launchCompilation_Minimal_Css()
            throws IOException, CompilationException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);

        final File inputFile = this.folder.newFile("input");
        final String cssText = "body{text-align:left;}";
        FileUtils.write(inputFile, cssText);

        // Call
        final Output output = compiler.launchCompilation(inputFile.toURI(),
                this.folder.newFile("output").toURI());

        // Test
        Assert.assertTrue(StringUtils.isNotBlank(output.getCss()));
        Mockito.verify(this.logger, Mockito.never()).error(Mockito.anyString());
    }

    @Test
    public void launchCompilation_Incorrect_Css()
            throws IOException, CompilationException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);

        final File inputFile = this.folder.newFile("input");
        final String cssText = "body{";
        FileUtils.write(inputFile, cssText);

        // Call
        final Output output = compiler.launchCompilation(inputFile.toURI(),
                this.folder.newFile("output").toURI());

        // Test
        Assert.assertTrue(StringUtils.isBlank(output.getCss()));
        Mockito.verify(this.logger, Mockito.times(5))
                .error(Mockito.anyString());
    }

    @Test
    public void generateSourceMap_Generation() throws IOException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);
        final File sourceMapFile = this.folder.newFile();
        compiler.setSourceMapFile(sourceMapFile);
        final Output output = new Output(null, "sourceMap", 0, null, null, null,
                null, null);

        // Call
        compiler.generateSourceMap(output);

        // Check
        Assert.assertTrue(StringUtils
                .isNotBlank(FileUtils.readFileToString(sourceMapFile)));
        Mockito.verify(this.logger, Mockito.never()).error(Mockito.anyString(),
                Mockito.any(IOException.class));
    }

    @Test
    public void generateSourceMap_No_SourceMap() throws IOException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);
        final File sourceMapFile = this.folder.newFile();
        compiler.setSourceMapFile(sourceMapFile);
        final Output output = new Output(null, null, 0, null, null, null, null,
                null);

        // Call
        compiler.generateSourceMap(output);

        // Check
        Assert.assertTrue(
                StringUtils.isBlank(FileUtils.readFileToString(sourceMapFile)));
        Mockito.verify(this.logger, Mockito.never()).error(Mockito.anyString(),
                Mockito.any(IOException.class));
    }

    @Test
    public void generateSourceMap_IOException() throws IOException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);
        final File sourceMapFile = new File("");
        compiler.setSourceMapFile(sourceMapFile);
        final Output output = new Output(null, "sourceMap", 0, null, null, null,
                null, null);

        // Call
        compiler.generateSourceMap(output);

        // Check
        Mockito.verify(this.logger, Mockito.times(1)).error(Mockito.anyString(),
                Mockito.any(IOException.class));
    }

    @Test
    public void writeCss() throws IOException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);
        final File outputFile = this.folder.newFile();
        final Output output = new Output("css", null, 0, null, null, null, null,
                null);

        // Call
        compiler.writeCss(outputFile, output);

        // Check
        Assert.assertTrue(
                StringUtils.isNotBlank(FileUtils.readFileToString(outputFile)));
        Mockito.verify(this.logger, Mockito.never()).error(Mockito.anyString(),
                Mockito.any(IOException.class));
    }

    @Test
    public void writeCss_IOException() throws IOException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(this.logger);
        final File outputFile = new File(
                this.folder.getRoot().getAbsolutePath() + File.separator + "?");

        final Output output = new Output("css", null, 0, null, null, null, null,
                null);

        // Call
        compiler.writeCss(outputFile, output);

        // Check
        Mockito.verify(this.logger, Mockito.times(1)).error(Mockito.anyString(),
                Mockito.any(IOException.class));
    }
}
