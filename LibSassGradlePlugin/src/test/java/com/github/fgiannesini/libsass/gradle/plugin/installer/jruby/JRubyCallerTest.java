package com.github.fgiannesini.libsass.gradle.plugin.installer.jruby;

import java.io.File;
import java.io.IOException;

import org.gradle.api.logging.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JRubyCallerTest {

    @Mock
    private Logger logger;

    @Test
    public void generateTempFolder() throws IOException {
        // Prepare
        final JRubyCaller jrubyCaller = new JRubyCaller(this.logger);

        // Call
        final File folder = jrubyCaller
                .generateTempFolder("forLibSassGradlePluginTest");

        // Check
        Assert.assertTrue(folder.exists());
        Assert.assertTrue(folder.isDirectory());
    }

    @Test
    @Ignore
    public void launchBourbonDownload() throws IOException {
        final String gemName = "bourbon";
        final JRubyCaller jRubyCaller = new JRubyCaller(this.logger);
        final File file = jRubyCaller.unpack(gemName, null);
        Assert.assertTrue(file.exists());
    }

    @Test
    @Ignore
    public void launchCompassDownload() throws IOException {
        final String gemName = "compass-core";
        final JRubyCaller jRubyCaller = new JRubyCaller(this.logger);
        final File file = jRubyCaller.unpack(gemName, null);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void addVersionToArgs_Last_Version() {
        // Prepare
        final JRubyCaller jRubyCaller = new JRubyCaller(this.logger);

        // Call
        final String[] args = jRubyCaller.addVersionToArgs(null, new String[0]);

        // Check
        Assert.assertEquals(0, args.length);
    }

    @Test
    public void addVersionToArgs_Specific_Version() {
        // Prepare
        final JRubyCaller jRubyCaller = new JRubyCaller(this.logger);

        // Call
        final String[] args = jRubyCaller.addVersionToArgs("1.0",
                new String[0]);

        // Check
        Assert.assertEquals(2, args.length);
        Assert.assertEquals(args[0], "-v");
        Assert.assertEquals(args[1], "1.0");
    }
}
