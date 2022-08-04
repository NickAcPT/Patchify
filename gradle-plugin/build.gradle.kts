plugins {
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
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
}