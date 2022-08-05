package io.github.nickacpt.patchify.gradle.extension

import io.github.nickacpt.patchify.gradle.model.PatchifyGradleWorkspace
import io.github.nickacpt.patchify.gradle.model.builders.PatchifyWorkspaceBuilder
import org.gradle.api.Project

typealias WorkspaceBuilder = PatchifyWorkspaceBuilder.() -> Unit

open class PatchifyExtension {
    internal lateinit var project: Project
    private val workspaceBuilders = mutableMapOf<String, WorkspaceBuilder>()
    internal val workspaces = mutableListOf<PatchifyGradleWorkspace>()

    fun workspace(name: String, builder: WorkspaceBuilder) {
        workspaceBuilders[name] = builder
    }

    fun projectWorkspace(name: String, project: Project, builder: WorkspaceBuilder = {}) {
        val extProject = this@PatchifyExtension.project
        workspace(name) {
            source = project.projectDir.toPath()
            patches =
                extProject.projectDir.toPath().resolve("patches").resolve(name.lowercase())
            this.builder()
        }
    }

    internal fun buildWorkspaces() {
        workspaceBuilders.forEach { (name, builder) ->
            workspaces.add(PatchifyWorkspaceBuilder().apply { this.name = name }.apply(builder).build())
        }
    }
}