package com.github.fgiannesini.libsass.gradle.plugin.installer.jruby;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.logging.Logger;
import org.jruby.Main;

import lombok.AllArgsConstructor;

/**
 * Call JRuby functions
 */
@AllArgsConstructor
public class JRubyCaller {

    /**
     * Gradle logger
     */
    private final Logger logger;

    /**
     * Unpack gem in temporary folder (auto delete on exit)
     *
     * @param gemName
     * @param gemVersion
     *            explicit version, last version if null
     * @return temporary folder where gem is unpacked
     * @throws IOException
     */
    public File unpack(final String gemName, final String gemVersion)
            throws IOException {
        if (this.logger.isInfoEnabled()) {
            this.logger.info("Downloading " + gemName);
        }

        // Generating temporary folder for gemUnpacking
        final File folder = this.generateTempFolder(gemName);

        String[] unpackArgs = { "-S", "gem", "unpack", gemName, "--target",
                folder.getAbsolutePath() };

        unpackArgs = this.addVersionToArgs(gemVersion, unpackArgs);

        // Launch JRuby with parameters
        Main.main(unpackArgs);

        if (this.logger.isInfoEnabled()) {
            this.logger.info(gemName + "downloaded");
        }
        return folder;
    }

    protected File generateTempFolder(final String gemName) throws IOException {
        final File folder = File.createTempFile(gemName, "");
        FileUtils.deleteQuietly(folder);
        FileUtils.forceMkdir(folder);
        FileUtils.forceDeleteOnExit(folder);
        return folder;
    }

    protected String[] addVersionToArgs(final String gemVersion,
            String[] unpackArgs) {
        // if version is set, add parameter
        if (StringUtils.isNotBlank(gemVersion)) {
            this.logger.info("Version : " + gemVersion);
            unpackArgs = ArrayUtils.addAll(unpackArgs, "-v", gemVersion);
        } else {
            this.logger.info("Version : Last");
        }
        return unpackArgs;
    }

}
