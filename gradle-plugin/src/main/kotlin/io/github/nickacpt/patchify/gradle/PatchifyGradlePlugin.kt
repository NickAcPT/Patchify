package io.github.nickacpt.patchify.gradle

import io.github.nickacpt.patchify.core.tasks.impl.InitSourceDirectoryTask
import io.github.nickacpt.patchify.gradle.extension.PatchifyExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.io.path.relativeTo

class PatchifyGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val patchifyExtension = target.extensions.create("patchify", PatchifyExtension::class.java).apply { project = target }

        target.afterEvaluate {
            patchifyExtension.buildWorkspaces()

            patchifyExtension.workspaces.forEach {
                println("Workspace: ${it.sourceDirectory.relativeTo(target.projectDir.toPath())}/${it.patchesDirectory.relativeTo(target.projectDir.toPath())}")
                InitSourceDirectoryTask(it).run()
            }
        }
    }
}