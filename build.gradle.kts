import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"

    application
}

group = "io.github.nickacpt"
version = "2.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://repo.eclipse.org/content/groups/releases/") }
}

tasks {
    shadowJar {
        archiveClassifier.set(null as String?)
    }
}


application {
    mainClass.set("io.github.nickac.patchify.MainKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

tasks {
    shadowJar {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClass.get()))
        }
    }
}
dependencies {

    /* Kotlin std-lib */
    implementation(kotlin("stdlib-jdk8"))
    /* Kotlinx serialization */
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    /* Command line parser */
    implementation("com.github.ajalt:clikt:2.8.0")

    /* JGit */
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.1.0.202203080745-r")

    /* No-op slf4j logger */
    implementation("org.slf4j", "slf4j-nop", "2.0.0-alpha7")
}
