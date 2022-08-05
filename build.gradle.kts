plugins {
    kotlin("jvm") version "1.7.10" apply false
}

allprojects {
    group = "io.github.nickacpt.patchify"
    version = "2.1.0-SNAPSHOT"
}

arrayOf("core", "cli", "gradle-plugin").forEach { name ->
    project(name) {
        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "java-library")
        apply(plugin = "maven-publish")

        this.repositories {
            mavenCentral()
        }

        this.dependencies {
            /* Kotlin std-lib */
            "implementation"(kotlin("stdlib-jdk8"))
        }

        val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by this.tasks
        compileKotlin.kotlinOptions {
            jvmTarget = "1.8"
        }

        this.extensions.getByType<PublishingExtension>().apply {
            repositories {
                val lightCraftRepoDir = project.findProperty("lightcraft.repo.location")
                if (lightCraftRepoDir != null) {
                    maven {
                        this.name = "OrionCraftRepo"
                        this.url = File(lightCraftRepoDir.toString()).toURI()
                    }
                }
            }

            publications {
                create<MavenPublication>("mavenJava") {
                    this.from(components["java"])
                    this.artifactId = "patchify-${project.name}"
                }
            }
        }
    }
}