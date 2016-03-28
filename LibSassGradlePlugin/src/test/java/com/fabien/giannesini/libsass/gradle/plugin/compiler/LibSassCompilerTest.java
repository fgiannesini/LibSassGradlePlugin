package com.fabien.giannesini.libsass.gradle.plugin.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.testng.Assert;

import com.fabien.giannesini.libsass.gradle.plugin.TestLogger;

import io.bit3.jsass.CompilationException;

public class LibSassCompilerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void compile_Empty_File() throws IOException, CompilationException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(new TestLogger());
        final File inputFile = this.folder.newFile("input");
        final File outputFile = Paths
                .get(this.folder.getRoot().getAbsolutePath(), "output")
                .toFile();

        // Call
        compiler.compileFile(inputFile, outputFile);

        // Test
        Assert.assertTrue(outputFile.exists());
    }

    @Test
    public void compile_Minimal_Css() throws IOException, CompilationException {
        // Prepare
        final LibSassCompiler compiler = new LibSassCompiler(new TestLogger());
        final File inputFile = this.folder.newFile("input");
        final String cssText = "body{text-align:left;}";
        FileUtils.write(inputFile, cssText);
        final File outputFile = Paths
                .get(this.folder.getRoot().getAbsolutePath(), "output")
                .toFile();

        // Call
        compiler.compileFile(inputFile, outputFile);

        // Test
        Assert.assertTrue(outputFile.exists());
        Assert.assertTrue(
                StringUtils.isNotBlank(FileUtils.readFileToString(outputFile)));
    }

}
