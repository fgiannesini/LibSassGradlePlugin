package com.fabien.giannesini.libsass.gradle.plugin.jruby;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fabien.giannesini.libsass.gradle.plugin.TestLogger;
import com.github.fgiannesini.libsass.gradle.plugin.jruby.JRubyCaller;

public class JRubyCallerTest {

    @Test
    public void launchBourbonDownload() throws IOException {
        final String gemName = "bourbon";
        final JRubyCaller jRubyCaller = new JRubyCaller(new TestLogger());
        final File file = jRubyCaller.unpack(gemName, null);
        Assert.assertTrue(file.exists());
    }

    @Test
    public void launchCompassDownload() throws IOException {
        final String gemName = "compass";
        final JRubyCaller jRubyCaller = new JRubyCaller(new TestLogger());
        final File file = jRubyCaller.unpack(gemName, null);
        Assert.assertTrue(file.exists());
    }
}
