package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.gradle.api.logging.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ScssFrameworkInstallerTest {

    @Mock
    private Logger logger;

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = Exception.class)
    public void getAndCheckUnpackedSourceFolder_No_Folder() throws Exception {
        // Prepare
        final File source = this.folder.getRoot();

        // Call
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        installer.getAndCheckUnpackedSourceFolder("test", source);
    }

    @Test(expected = Exception.class)
    public void getAndCheckUnpackedSourceFolder_Many_Folders()
            throws Exception {
        // Prepare
        final File source = this.folder.getRoot();
        this.folder.newFolder();
        this.folder.newFolder();

        // Call
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        installer.getAndCheckUnpackedSourceFolder("test", source);
    }

    @Test
    public void getAndCheckUnpackedSourceFolder_One_Folder() throws Exception {
        // Prepare
        final File source = this.folder.getRoot();
        final File unpackedFolder = this.folder.newFolder();

        // Call
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        final File unpackedSourceFolder = installer
                .getAndCheckUnpackedSourceFolder("test", source);

        // Check
        Assert.assertEquals(unpackedSourceFolder, unpackedFolder);
        Mockito.verify(this.logger, Mockito.never()).error(Mockito.anyString());
    }

    @Test
    public void getSourcesFolder() throws Exception {
        // Prepare
        final File source = this.folder.getRoot();
        this.folder.newFolder();

        // Call
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        final File unpackedSourceFolder = installer
                .getSourcesFolder(FrameworkPropertiesEnum.BOURBON, source);

        // Check
        Assert.assertNotNull(unpackedSourceFolder);
    }

    @Test(expected = IOException.class)
    public void getInstallationPath_No_InstallationPath() throws IOException {
        // Call
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        installer.getInstallationPath("test");
    }

    @Test
    public void getInstallationPath() throws IOException {
        // Prepare
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        installer.setInstallationPath(Paths.get(""));

        // Call
        final Path installationPath = installer.getInstallationPath("test");

        Assert.assertNotNull(installationPath);
    }

    @Test
    public void copySourcesToInstallationPath_No_Files() throws IOException {
        // Call
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        installer.copySourcesToInstallationPath(this.folder.getRoot(), null);

        // Check
        Mockito.verify(this.logger, Mockito.times(1))
                .error(Mockito.anyString());
    }

    @Test
    public void copySourcesToInstallationPath() throws IOException {
        // Prepare
        this.folder.newFolder();
        this.folder.newFile();

        // Call
        final ScssFrameworkInstaller installer = new ScssFrameworkInstaller(
                this.logger);
        final File root = this.folder.getRoot();
        final Path installationPath = Paths.get(root.getAbsolutePath(),
                "install");
        installer.copySourcesToInstallationPath(root, installationPath);

        // Check
        Mockito.verify(this.logger, Mockito.times(2)).info(Mockito.anyString());
        final File installationFolder = installationPath.toFile();
        final File[] listFiles = installationFolder.listFiles();
        Assert.assertNotNull(listFiles);
        Assert.assertEquals(listFiles.length, 2);
        Assert.assertEquals(
                Arrays.stream(listFiles).filter(File::isFile).count(), 1);
        Assert.assertEquals(
                Arrays.stream(listFiles).filter(File::isDirectory).count(), 1);
    }
}
