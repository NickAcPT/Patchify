plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"

    application
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "io.github.nickacpt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://repo.eclipse.org/content/groups/releases/") }
}

tasks {
    shadowJar {
        classifier = ""
    }
}

distributions {
}

application {
    mainClassName = "io.github.nickac.patchify.MainKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    shadowJar {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }
dependencies {

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0") // JVM dependency

    implementation("com.github.ajalt:clikt:2.7.1")
    implementation("org.eclipse.jgit", "org.eclipse.jgit", "5.8.0.202006091008-r")
    implementation("org.slf4j", "slf4j-nop", "2.0.0-alpha1")

}