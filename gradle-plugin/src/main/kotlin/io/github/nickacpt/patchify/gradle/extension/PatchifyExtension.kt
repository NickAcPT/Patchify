package io.github.nickacpt.patchify.gradle.extension

import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.gradle.model.PatchifyWorkspaceBuilder
import org.gradle.api.Project

typealias WorkspaceBuilder = PatchifyWorkspaceBuilder.() -> Unit

open class PatchifyExtension {
    internal lateinit var project: Project
    private val workspaceBuilders = mutableListOf<WorkspaceBuilder>()
    internal val workspaces = mutableListOf<PatchifyWorkspace>()

    fun workspace(builder: PatchifyWorkspaceBuilder.() -> Unit) {
        workspaceBuilders.add(builder)
    }

    fun projectWorkspace(project: Project) {
        val extProject = this@PatchifyExtension.project
        workspace {
            source = project.projectDir.toPath()
            patches =
                extProject.projectDir.toPath().resolve("patches").resolve(project.name.lowercase())
        }
    }

    internal fun buildWorkspaces() {
        workspaceBuilders.forEach {
            workspaces.add(PatchifyWorkspaceBuilder().apply(it).build())
        }
    }
}