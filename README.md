# LibSassGradlePlugin
A gradle plugin to compile scss/sass with jsass (libsass wrapper for java)
- _Need Gradle Version >= 2.12_
- _Need Java Version >= 8_

##Available tasks
- **compileLibSass** : Compile sass/scss files to css (with optional source map) with LibSass
- **compileLibSassWithWatch** : Continuous compilation of sass/scss files to css (with optional source map) with LibSass

##Next (If possible)
- Task to install compass
- Task to install bourbon

##Configuration

```groovy
apply plugin: 'java'
apply plugin: 'com.github.fgiannesini.libsass.gradle.plugin'

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.fgiannesini.libsass.gradle.plugin:libsass-gradle-plugin:+'
    }
}

libSassParameters {
	//Type: String Mandatory
	//File path to compile (normally import other scss files)
	inputFilePath = "src/main/resources/scss/input.scss"
	
	//Type: String Mandatory
	//Compiled file path
	outputFilePath = "src/main/resources/css/output.css"
	
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
	//sourceMapFilePath "src/main/resources/css/output.css.map"
	
	//Type: String Default: nested Values: nested, expanded, compact, compressed
	//Determines the output format of the final CSS style.
	//outputStyle "nested"
	
	//Type: String Default: ""
	//Paths that LibSass can look in to attempt to resolve your @import declarations. When using data, it is recommended that you use this. 
	//';' is the path separator for Windows
	//':' is the path separator for Linux
	//includePaths "src/main/resources/scss-lib"
	
	//Type: String
    	//Installation folder for bourbon sources
    	bourbonInstallationPath "src/main/resources/scss"

	//Type: String
    	//If a specific version of bourbon is necessary, last by defaut
    	//bourbonVersion "4.2.6"

	//Type: String
    	//Installation folder for compass sources
    	compassInstallationPath "src/main/resources/scss"

	//Type: String
    	//If a specific version of compass is necessary, last by defaut
    	//compassVersion "1.0.3"
}
```
