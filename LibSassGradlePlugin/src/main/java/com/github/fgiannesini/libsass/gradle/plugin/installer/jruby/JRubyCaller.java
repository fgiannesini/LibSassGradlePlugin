package com.github.fgiannesini.libsass.gradle.plugin.installer.jruby;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
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
        this.logger.info("Downloading " + gemName);

        // Generating temporary folder for gemUnpacking
        final File folder = File.createTempFile(gemName, "");
        FileUtils.deleteQuietly(folder);
        FileUtils.forceMkdir(folder);
        FileUtils.forceDeleteOnExit(folder);

        String[] unpackArgs = { "-S", "gem", "unpack", gemName, "--target",
                folder.getAbsolutePath() };

        // if version is set, add parameter
        if (StringUtils.isNotBlank(gemVersion)) {
            this.logger.info("Version : " + gemVersion);
            final String[] unpackArgsWithVersion = Arrays.copyOf(unpackArgs,
                    unpackArgs.length + 2);
            unpackArgsWithVersion[unpackArgs.length] = "-v";
            unpackArgsWithVersion[unpackArgs.length + 1] = gemVersion;
            unpackArgs = unpackArgsWithVersion;
        } else {
            this.logger.info("Version : last version");
        }

        // Launch JRuby with parameters
        Main.main(unpackArgs);

        return folder;
    }

}
