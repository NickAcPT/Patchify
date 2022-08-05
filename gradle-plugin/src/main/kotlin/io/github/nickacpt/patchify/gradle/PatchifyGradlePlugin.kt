package io.github.nickacpt.patchify.gradle

import io.github.nickacpt.patchify.gradle.extension.PatchifyExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class PatchifyGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val patchifyExtension = target.extensions.create("patchify", PatchifyExtension::class.java).apply { project = target }

        target.afterEvaluate { p ->
            patchifyExtension.buildWorkspaces()

            PatchifyGradle.initialize(p)
        }
    }
}