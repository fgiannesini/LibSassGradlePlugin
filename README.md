# LibSassGradlePlugin
A gradle plugin to compile scss/sass with jsass (libsass wrapper for java)
- _Need Gradle Version >= 2.12_
- _Need Java Version >= 8_

##Available tasks
_1.0_
- **compileLibSass** : Compile sass/scss files to css (with optional source map) with LibSass
- **compileLibSassWithWatch** : Continuous compilation of sass/scss files to css (with optional source map) with LibSass

_1.1_
- **installBourbon** : import bourbon scss source files in current project
- **installCompass** : import compass scss source files in current project

##Next
- Maintenance

##Limitations
- **installCompass** : Source modifications are applied by the plugin to avoir compilation errors:
  * Correct import path, compass uses absolute path, libsass needs relative path.
  * Compass calls "prefix-usage", a ruby function from compass gem. An error is thrown by Libsass on compilation.
This function is used to make stats to avoid deprecated browser prefixes. 
The choice here is to replace this function by a constant to always add prefixes even if they are deprecated. 
(workaround: https://github.com/sass/libsass/issues/1936)


##Configuration

```groovy
apply plugin: 'java'
apply plugin: 'com.github.fgiannesini.libsass.gradle.plugin'

buildscript {
    repositories {
    	mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.fgiannesini.libsass.gradle.plugin:libsass-gradle-plugin:+'
    }
}

//On parameters, file paths can be relative from resource folder or project root folder
libSassParameters {
	//Type: String Mandatory
	//File path to compile (normally import other scss files)
	inputFilePath = "scss/input.scss"
	
	//Type: String Mandatory
	//Compiled file path
	outputFilePath = "css/output.css"
	
	//Directory to watch for continuous compilation 	
	watchedDirectoryPath = "src/main/resources/scss"
	
	//Type: Boolean Default: false
	//'true' embeds the source map as a data URI
	sourceMapEmbed = true
	
	//Type: Boolean Default: false
	//'true' enables additional debugging information in the output file as CSS comments 
	//sourceComments = false
	
	//Type: Boolean Default: false 
	//'true' values disable the inclusion of source map information in the output file
	//omitSourceMappingURL = false
	
	//Type: Boolean Default: false
	//'true' includes the contents in the source map information
	//sourceMapContents = false
	
	//Type: String Default: 'scss'
	//Input syntax 'scss' or 'sass'
	//inputSyntax "scss"
	
	//Type: Integer Default: 5
	//Used to determine how many digits after the decimal will be allowed. For instance, if you had a decimal number of 1.23456789 and a precision of 5, the result will be 1.23457 in the final CSS.
	//precision 5
	
	//Type: String 
	//Path of source file to generate if not embedded
	//sourceMapFilePath "css/output.css.map"
	
	//Type: String Default: nested Values: nested, expanded, compact, compressed
	//Determines the output format of the final CSS style.
	//outputStyle "nested"
	
	//Type: String Default: ""
	//Paths that LibSass can look in to attempt to resolve your @import declarations. When using data, it is recommended that you use this. 
	//';' is the path separator for Windows
	//':' is the path separator for Linux
	//includePaths "scss-lib"
	
	//Type: String
    //Installation folder for bourbon sources
    bourbonInstallationPath "scss"

	//Type: String
    //If a specific version of bourbon is necessary
    //bourbonVersion "4.2.6"

	//Type: String
    //Installation folder for compass sources
    compassInstallationPath "src/main/resources/scss"

	//Type: String
    //If a specific version of compass is necessary
    //compassVersion "1.0.3"
}
```
