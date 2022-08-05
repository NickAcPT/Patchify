# Patchify

Tool used to manage/automate code projects that require source-code patching. This project was inspired by the tool used
by [PaperMC](https://github.com/PaperMC/Paper).

## Gradle Plugin

The Gradle plugin is used to automatically manage the source-code patching process for projects that use Gradle without
requiring manual use of the cli tool.

To use the Gradle plugin, add the following to your settings.gradle.kts file:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.quiltmc.org/repository/release/")
        maven("https://raw.githubusercontent.com/NickAcPT/LightCraftMaven/main/")
    }
}
```

Now that the required plugin repositories have been added, Gradle can now find the plugin. To add it to your project,
add the following to your build.gradle.kts file:

```kotlin
plugins {
    id("io.github.nickacpt.patchify.gradle") version "2.1.0-SNAPSHOT"
}
```

Then you can define the patchify configuration in your build.gradle.kts file:

```kotlin
patchify {
    projectWorkspace(/* Name */ "Api", /* Project */ project(":sample-api")) {
        initializer {
            decompileJar(project.file("path/to/jar/to/decompile.jar"), "src/main/java")
        }
    }
}
```

When using the `projectWorkspace` workspace definition, the Patchify Gradle plugin will automatically find the best paths
to use.

If you want Patchify to handle decompilation and patching for you, use the `decompileJar` initializer. This is the step
that is done to prepare the source-code for patching.

## Command-line interface

Patchify also provides a command-line interface that can be used to manage and automate patching of source-code.

```
Usage: patchify [OPTIONS] COMMAND [ARGS]...

  Patchify is a tool used to manage/automate code projects that require
  source-code patching.

Options:
  -h, --help  Show this message and exit

Commands:
  init             Initialize a new patchify workspace
  apply-patches    Apply all patches to the source code
  rebuild-patches  Rebuilds all patches
```

### Commands

|         Command         | Description                                                                  |
|:-----------------------:|------------------------------------------------------------------------------|
|         `init`          | Initializes a git repository to be able to patch source-code.                |
|    `rebuild-patches`    | Rebuild patch files through the commits done to the source directory.        |
|     `apply-patches`     | Applies the patches on the `patches` directory to the `source` directory     |
