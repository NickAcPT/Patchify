import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") apply true
    id ("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    /* Kotlin std-lib */
    implementation(kotlin("stdlib-jdk8"))

    /* Command line parser */
    implementation("com.github.ajalt.clikt:clikt:3.4.2")

    /* Patchify Core */
    implementation(project(":core"))

    /* Jackson - Json Parser */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}