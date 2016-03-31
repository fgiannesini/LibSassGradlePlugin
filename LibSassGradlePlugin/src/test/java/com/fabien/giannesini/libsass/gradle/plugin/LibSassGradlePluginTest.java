package com.fabien.giannesini.libsass.gradle.plugin;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.github.fgiannesini.libsass.gradle.plugin.tasks.CompileLibSassTask;

public class LibSassGradlePluginTest {

	@Test
	public void checkPlugin_Compile_Task() {
		final Project project = ProjectBuilder.builder().build();
		project.getPlugins().apply("com.github.fgiannesini.libsass.gradle.plugin");
		Assert.assertTrue(project.getTasks().getByName("compileLibSass") instanceof CompileLibSassTask);
	}
}
