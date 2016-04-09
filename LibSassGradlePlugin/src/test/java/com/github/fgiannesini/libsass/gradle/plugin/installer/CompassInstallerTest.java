package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.Assert;

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

        final File scssFileLevel0 = this.folder.newFile(".scss");
        FileUtils.write(scssFileLevel0, "@import compass/test0");
        final File folderLevel0 = this.folder.newFolder();
        final File scssFileLevel1 = Paths
                .get(folderLevel0.getAbsolutePath(), ".scss").toFile();
        FileUtils.write(scssFileLevel1, System.lineSeparator()
                + System.lineSeparator() + "     @import compass/test10");
        final File txtFileLevel1 = Paths
                .get(folderLevel0.getAbsolutePath(), ".txt").toFile();
        FileUtils.write(txtFileLevel1, "import compass/test11");

        // Call
        compassInstaller.correctFilesImports(this.folder.getRoot(), 0);

        // Check
        Assert.assertEquals("@import compass/test0" + System.lineSeparator(),
                FileUtils.readFileToString(scssFileLevel0));
        Assert.assertEquals(System.lineSeparator() + System.lineSeparator()
                + "     @import ../compass/test10" + System.lineSeparator(),
                FileUtils.readFileToString(scssFileLevel1));
        Assert.assertEquals("import compass/test11",
                FileUtils.readFileToString(txtFileLevel1));
    }
}
