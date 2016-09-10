package com.github.fgiannesini.libsass.gradle.plugin.extension;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;

import com.github.fgiannesini.libsass.gradle.plugin.compiler.PluginInputSyntax;
import com.github.fgiannesini.libsass.gradle.plugin.compiler.PluginOutputStyle;

/**
 * Plugin parameters provider. Parameters can be for development only 
 * retrieved from {@link PluginParameters} <br>
 * Parameters can be for production : retrieved from
 * {@link PluginProductionParameters} if defined or defaut
 * {@link PluginParameters}<br>
 * Parameters are checked and transformed to be used by the plugin
 *
 */
public class PluginParametersProvider {

	/**
	 * Parameters for developpement build
	 */
	private final PluginParameters parameters;

	/**
	 * Parameters for production build
	 */
	private PluginProductionParameters productionParameters;

	/**
	 * Project root path
	 */
	private final Project project;

	public PluginParametersProvider(final Project project, final PluginMode pluginMode) {
		this.parameters = project.getExtensions().getByType(PluginParameters.class);

		if (this.parameters != null && PluginMode.PRODUCTION.equals(pluginMode)) {
			this.productionParameters = ExtensionAware.class.cast(this.parameters).getExtensions()
					.findByType(PluginProductionParameters.class);
		}
		this.project = project;
	}

	/**
	 * Default: {gradle resources}/scss/{projectName}.scss
	 */
	public URI getInputUri() {

		String value = null;
		String productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getInputFilePath();
		}

		String devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getInputFilePath();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}

		if (value == null) {
			value = "scss" + File.separator + this.project.getName().toLowerCase() + ".scss";
		}

		final String path = this.project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets()
				.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getAsPath();
		final File file = Paths.get(path, value).toFile();

		if (!file.exists()) {
			throw new IllegalArgumentException("Input file " + file.getAbsolutePath() + " does not exist ");
		}
		return file.toURI();
	}

	/**
	 * Default: {gradle resources}/css/{projectName}.css
	 */
	public URI getOutputUri() {

		String value = null;
		String productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getOutputFilePath();
		}

		String devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getOutputFilePath();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}

		if (value == null) {
			value = "css" + File.separator + this.project.getName().toLowerCase() + ".scss";
		}

		final String path = this.project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets()
				.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getAsPath();
		return Paths.get(path, value).toUri();
	}

	/**
	 * Default: false 'true' includes the contents in the source map information
	 */
	public Boolean getSourceMapContents() {
		Boolean value = Boolean.FALSE;
		Boolean productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getSourceMapContents();
		}

		Boolean devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getSourceMapContents();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value;
	}

	/**
	 * Default: false 'true' embeds the source map as a data URI
	 */
	public Boolean getSourceMapEmbed() {
		Boolean value = Boolean.FALSE;
		Boolean productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getSourceMapEmbed();
		}

		Boolean devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getSourceMapEmbed();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value;
	}

	/**
	 * Default: false. 'true' enables additional debugging information in the
	 * output file as CSS comments
	 */
	public Boolean getSourceComments() {
		Boolean value = Boolean.FALSE;
		Boolean productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getSourceComments();
		}

		Boolean devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getSourceComments();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value;
	}

	/**
	 * Default: false 'true' values disable the inclusion of source map
	 * information in the output file
	 */
	public boolean getOmitSourceMappingUrl() {
		Boolean value = Boolean.FALSE;
		Boolean productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getOmitSourceMappingURL();
		}

		Boolean devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getOmitSourceMappingURL();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value.booleanValue();
	}

	/**
	 * Default: 'scss' Input syntax 'scss' or 'sass'
	 */
	public PluginInputSyntax getInputSyntax() {
		PluginInputSyntax value = PluginInputSyntax.SCSS;
		PluginInputSyntax productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = PluginInputSyntax.valueOf(this.productionParameters.getInputSyntax());
		}

		PluginInputSyntax devParameter = null;
		if (this.parameters != null) {
			devParameter = PluginInputSyntax.valueOf(this.parameters.getInputSyntax());
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value;
	}

	/**
	 * Default: nested Values: nested, expanded, compact, compressed Determines
	 * the output format of the final CSS style.
	 */
	public PluginOutputStyle getOutputStyle() {
		PluginOutputStyle value = PluginOutputStyle.NESTED;
		PluginOutputStyle productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = PluginOutputStyle.valueOf(this.productionParameters.getOutputStyle());
		}

		PluginOutputStyle devParameter = null;
		if (this.parameters != null) {
			devParameter = PluginOutputStyle.valueOf(this.parameters.getOutputStyle());
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value;
	}

	/**
	 * Default: 5 Used to determine how many digits after the decimal will be
	 * allowed. For instance, if you had a decimal number
	 */
	public int getPrecision() {
		Integer value = Integer.valueOf(5);
		Integer productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = Integer.valueOf(this.productionParameters.getInputSyntax());
		}

		Integer devParameter = null;
		if (this.parameters != null) {
			devParameter = Integer.valueOf(this.parameters.getInputSyntax());
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value.intValue();
	}

	/**
	 * Default: "" Paths that LibSass can look in to attempt to resolve your
	 * declarations. When using data, it is recommended that you use this. ';'
	 * is the path separator for Windows ':' is the path separator for Linux
	 */
	public String[] getIncludePaths() {
		String value = "";
		String productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getIncludePaths();
		}

		String devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getIncludePaths();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}
		return value.split(File.pathSeparator);
	}

	/**
	 * Path of source file to generate if not embedded
	 */
	public URI getSourceMapUri() {
		String value = null;
		String productionParameter = null;
		if (this.productionParameters != null) {
			productionParameter = this.productionParameters.getSourceMapFilePath();
		}

		String devParameter = null;
		if (this.parameters != null) {
			devParameter = this.parameters.getSourceMapFilePath();
		}

		if (productionParameter != null) {
			value = productionParameter;
		} else if (devParameter != null) {
			value = devParameter;
		}

		final String path = this.project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets()
				.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getAsPath();
		return Paths.get(path, value).toUri();
	}

	/**
	 * Path for compass installation
	 */
	public Path getCompassInstallationPath() {
		String value = "scss";

		if (this.parameters != null) {
			value = this.parameters.getCompassInstallationPath();
		}

		final String path = this.project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets()
				.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getAsPath();

		return Paths.get(path, value);
	}

	public String getCompassVersion() {
		String value = "";
		if (this.parameters != null) {
			value = this.parameters.getCompassVersion();
		}
		return value;
	}

	/**
	 * Path for bourbon installation
	 */
	public Path getBourbonInstallationPath() {
		String value = "scss";

		if (this.parameters != null) {
			value = this.parameters.getBourbonInstallationPath();
		}

		final String path = this.project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets()
				.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getAsPath();

		return Paths.get(path, value);
	}

	public String getBourbonVersion() {
		String value = "";
		if (this.parameters != null) {
			value = this.parameters.getBourbonVersion();
		}
		return value;
	}
}
