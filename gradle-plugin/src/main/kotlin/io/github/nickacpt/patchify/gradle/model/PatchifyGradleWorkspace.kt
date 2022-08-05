package io.github.nickacpt.patchify.gradle.model

import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.gradle.initializers.WorkspaceInitializer
import io.github.nickacpt.patchify.gradle.initializers.impl.EmptyWorkspaceInitializer
import java.nio.file.Files
import java.nio.file.Path

data class PatchifyGradleWorkspace(
    val name: String,
    val source: Path,
    val patches: Path,
    val initializer: WorkspaceInitializer = EmptyWorkspaceInitializer
) {
    val patchifyWorkspace: PatchifyWorkspace by lazy {
        PatchifyWorkspace(source, patches).also {
            arrayOf(source, patches).forEach { Files.createDirectories(it) }
        }
    }
}