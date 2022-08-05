package io.github.nickacpt.patchify.gradle.initializers

import io.github.nickacpt.patchify.core.model.PatchifyWorkspace

interface WorkspaceInitializer {
    fun initialize(workspace: PatchifyWorkspace)
}