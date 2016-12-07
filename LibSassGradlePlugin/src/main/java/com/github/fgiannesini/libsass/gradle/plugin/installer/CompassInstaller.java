package com.github.fgiannesini.libsass.gradle.plugin.installer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

/**
 * Specific installer for Compass
 */
public class CompassInstaller extends ScssFrameworkInstaller {

	public CompassInstaller(final Project project, final Logger logger) {
		super(logger);
	}

	@Override
	protected void copySourcesToInstallationPath(final File sourcesFolder, final Path installationPath)
			throws IOException {
		this.correctFilesImports(sourcesFolder, 0);
		super.copySourcesToInstallationPath(sourcesFolder, installationPath);
	}

	/**
	 * Correct compass files for libsass compilation. <br>
	 * <br>
	 *
	 *
	 * This method deals with files recursively
	 *
	 * @param sourcesFolder
	 * @param level
	 *            Folder depth in recursion
	 * @throws IOException
	 */
	protected void correctFilesImports(final File sourcesFolder, final int level) throws IOException {
		final File[] listFiles = sourcesFolder.listFiles();
		if (listFiles == null) {
			return;
		}
		for (final File file : listFiles) {
			if (file.isDirectory()) {
				this.correctFilesImports(file, level + 1);
			} else if (file.getName().endsWith(".scss")) {
				this.correctFileImport(level, file);
			}
		}
	}

	/**
	 * Correct import in compass File <br>
	 * <br>
	 * {@code @import
	 * "compass/support"} in file <i>compass/css3/_animation.scss</i> should
	 * become {@code @import "../../compass/support"} <br>
	 * <br>
	 *
	 * @param level
	 * @param file
	 * @throws IOException
	 */
	private void correctFileImport(final int level, final File file) throws IOException {
		this.logger.info("Correcting compass file " + file.getName());
		String fileContent = FileUtils.readFileToString(file, Charset.defaultCharset());
		fileContent = this.correctImport(level, fileContent);
		fileContent = this.correctRubyFunctionCall(fileContent);
		FileUtils.writeStringToFile(file, fileContent, Charset.defaultCharset());
	}

	/**
	 * Compass calls "prefix-usage", a ruby function from compass gem. An error
	 * is thrown by Libsass on compilation. This function is used to make stats
	 * to avoid deprecated browser prefixes. The choice here is to replace this
	 * function by a constant to always add prefixes even if they are
	 * deprecated. (https://github.com/sass/libsass/issues/1936)
	 *
	 * @param line
	 * @return
	 */
	private String correctRubyFunctionCall(String line) {
		final String stringToReplace = "prefix-usage";
		if (line.contains(stringToReplace)) {
			line = line.replace(stringToReplace, "0;//prefix-usage");
		}
		return line;
	}

	/**
	 * Correct import path, compass uses absolute path, libsass needs relative
	 * path.
	 *
	 * @param level
	 * @param fileContent
	 * @return
	 */
	private String correctImport(final int level, String fileContent) {
		final String originFolderPath = new StringBuilder("\"").append(StringUtils.repeat("../", level))
				.append("compass/").toString();
		fileContent = fileContent.replaceAll("\"compass/", originFolderPath);
		return fileContent;
	}
}
