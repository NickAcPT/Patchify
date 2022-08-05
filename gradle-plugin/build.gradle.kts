plugins {
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    maven("https://maven.quiltmc.org/repository/release/")
}

gradlePlugin {
    plugins {
        create("Patchify") {
            id = "io.github.nickacpt.patchify.gradle"
            implementationClass = "io.github.nickacpt.patchify.gradle.PatchifyGradlePlugin"
        }
    }
}


dependencies {
    implementation(project(":core"))
    // https://maven.quiltmc.org/repository/release/org/quiltmc/quiltflower/1.8.1/
    implementation("org.quiltmc:quiltflower:1.8.1")
}