package io.github.nickacpt.patchify.gradle.tasks

import io.github.nickacpt.patchify.gradle.model.PatchifyGradleWorkspace
import org.gradle.api.tasks.Input
import javax.inject.Inject

open class PatchifyWorkspaceTask @Inject constructor(@Input val workspace: PatchifyGradleWorkspace) : PatchifyTask() {
}