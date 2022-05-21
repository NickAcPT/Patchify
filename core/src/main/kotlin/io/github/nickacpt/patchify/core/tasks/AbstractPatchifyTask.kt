package io.github.nickacpt.patchify.core.tasks

import io.github.nickacpt.patchify.core.model.PatchifyWorkspace

abstract class AbstractPatchifyTask(val workspace: PatchifyWorkspace) {
    abstract val name: String

    abstract fun run()
}