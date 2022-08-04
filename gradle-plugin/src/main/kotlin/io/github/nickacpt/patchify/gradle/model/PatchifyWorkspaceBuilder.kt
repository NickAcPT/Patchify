package io.github.nickacpt.patchify.gradle.model

import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import java.nio.file.Files
import java.nio.file.Path

class PatchifyWorkspaceBuilder {
    lateinit var source: Path
    lateinit var patches: Path

    fun build() = PatchifyWorkspace(source, patches).also {
        arrayOf(source, patches).forEach { Files.createDirectories(it) }
    }
}