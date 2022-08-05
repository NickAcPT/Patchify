package io.github.nickacpt.patchify.gradle

import io.github.nickacpt.patchify.core.git.Git
import io.github.nickacpt.patchify.core.tasks.impl.ApplyPatchesTask
import io.github.nickacpt.patchify.core.tasks.impl.InitSourceDirectoryTask
import io.github.nickacpt.patchify.core.tasks.impl.RebuildPatchesTask
import io.github.nickacpt.patchify.gradle.model.PatchifyGradleWorkspace
import io.github.nickacpt.patchify.gradle.tasks.PatchifyTask
import io.github.nickacpt.patchify.gradle.tasks.PatchifyWorkspaceTask
import io.github.nickacpt.patchify.gradle.tasks.impl.ApplyWorkspacePatchesTask
import io.github.nickacpt.patchify.gradle.tasks.impl.CleanWorkspaceTask
import io.github.nickacpt.patchify.gradle.tasks.impl.RebuildWorkspacePatchesTask
import io.github.nickacpt.patchify.gradle.utils.patchifyExtension
import io.github.nickacpt.patchify.gradle.utils.registerWorkspaceTask
import org.gradle.api.Project
import java.nio.file.Files
import kotlin.io.path.name

object PatchifyGradle {

    fun rebuildWorkspacePatches(project: Project, workspace: PatchifyGradleWorkspace) {
        RebuildPatchesTask(workspace.patchifyWorkspace).run()
    }

    fun applyWorkspacePatches(project: Project, workspace: PatchifyGradleWorkspace) {
        ApplyPatchesTask(workspace.patchifyWorkspace).run()
    }

    fun initializeWorkspace(project: Project, workspace: PatchifyGradleWorkspace) {
        val isInitialized = with(Git(workspace.source)) {
            this.isInitialized()
        }
        if (isInitialized) return
        project.logger.quiet("patchify: initializing workspace ${workspace.source.name}")

        workspace.initializer.initialize(workspace.patchifyWorkspace)
        InitSourceDirectoryTask(workspace.patchifyWorkspace, false).run()
    }

    fun initialize(project: Project) {
        registerWorkspaceTask(project, "apply", ApplyWorkspacePatchesTask::class.java, "Patches")
        registerWorkspaceTask(project, "rebuild", RebuildWorkspacePatchesTask::class.java, "Patches")
        registerWorkspaceTask(project, "clean", CleanWorkspaceTask::class.java, "Workspace")
    }

    private fun registerWorkspaceTask(project: Project, actionName: String, taskClass: Class<out PatchifyWorkspaceTask>, actionSuffix: String) {
        val workspaceTasks = project.patchifyExtension.workspaces.map { workspace ->
            project.registerWorkspaceTask(
                "$actionName${workspace.name}$actionSuffix",
                workspace,
                taskClass
            )
        }

        project.tasks.register("${actionName}$actionSuffix", PatchifyTask::class.java) {
            it.dependsOn(workspaceTasks)
        }
    }

    fun cleanWorkspace(project: Project, workspace: PatchifyGradleWorkspace) {
        workspace.patchifyWorkspace.sourceDirectory.toFile().deleteRecursively()
        Files.createDirectories(workspace.patchifyWorkspace.sourceDirectory)
    }
}