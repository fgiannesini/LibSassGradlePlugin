package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CompassInstallerTest {

    @Mock
    private Project project;

    @Mock
    private Logger logger;

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void correctFilesImports() throws IOException {
        // Prepare
        final CompassInstaller compassInstaller = new CompassInstaller(
                this.project, this.logger);

        // Level 0
        final File scssFileLevel0 = this.folder.newFile(".scss");
        FileUtils.write(scssFileLevel0, "@import \"compass/test0\"",Charset.defaultCharset());

        final File folderLevel0 = this.folder.newFolder();

        // Level 1
        final File scssFileLevel1 = Paths
                .get(folderLevel0.getAbsolutePath(), ".scss").toFile();
        final String scssFileLevel1Input = new StringBuilder(
                System.lineSeparator())
                        .append("     @import \"compass/test10\"")
                        .append(System.lineSeparator())
                        .append("$usage: prefix-usage($prefix, $capability, $capability-options);")
                        .toString();
        FileUtils.write(scssFileLevel1, scssFileLevel1Input,Charset.defaultCharset());

        final File txtFileLevel1 = Paths
                .get(folderLevel0.getAbsolutePath(), ".txt").toFile();
        FileUtils.write(txtFileLevel1, "import \"compass/test11\"",Charset.defaultCharset());

        // Call
        compassInstaller.correctFilesImports(this.folder.getRoot(), 0);

        // Check
        Assert.assertEquals("@import \"compass/test0\"",
                FileUtils.readFileToString(scssFileLevel0,Charset.defaultCharset()));
        final String scssFileLvel1Result = new StringBuilder(
                System.lineSeparator())
                        .append("     @import \"../compass/test10\"")
                        .append(System.lineSeparator())
                        .append("$usage: 0;//prefix-usage($prefix, $capability, $capability-options);")
                        .toString();
        Assert.assertEquals(scssFileLvel1Result,
                FileUtils.readFileToString(scssFileLevel1,Charset.defaultCharset()));
        Assert.assertEquals("import \"compass/test11\"",
                FileUtils.readFileToString(txtFileLevel1, Charset.defaultCharset()));
    }
}
