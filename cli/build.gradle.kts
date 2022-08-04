plugins {
    id ("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    /* Command line parser */
    implementation("com.github.ajalt.clikt:clikt:3.5.0")

    /* Patchify Core */
    implementation(project(":core"))

    /* Jackson - Json Parser */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
}