package io.github.nickacpt.patchify.gradle.tasks.impl

import io.github.nickacpt.patchify.gradle.PatchifyGradle
import io.github.nickacpt.patchify.gradle.model.PatchifyGradleWorkspace
import io.github.nickacpt.patchify.gradle.tasks.PatchifyWorkspaceTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class ApplyWorkspacePatchesTask @Inject constructor(workspace: PatchifyGradleWorkspace) :
    PatchifyWorkspaceTask(workspace) {

    @TaskAction
    fun applyPatches() {
        PatchifyGradle.initializeWorkspace(project, workspace)
        PatchifyGradle.applyWorkspacePatches(project, workspace)
    }

}
