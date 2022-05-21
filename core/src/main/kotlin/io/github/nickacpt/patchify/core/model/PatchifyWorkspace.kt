package io.github.nickacpt.patchify.core.model

import java.nio.file.Path

data class PatchifyWorkspace(
    val sourceDirectory: Path,
    val patchesDirectory: Path
)
