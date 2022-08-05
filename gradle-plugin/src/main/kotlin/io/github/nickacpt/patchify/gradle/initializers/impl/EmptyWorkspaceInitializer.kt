package io.github.nickacpt.patchify.gradle.initializers.impl

import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.gradle.initializers.WorkspaceInitializer

object EmptyWorkspaceInitializer : WorkspaceInitializer {
    override fun initialize(workspace: PatchifyWorkspace) {
    }
}