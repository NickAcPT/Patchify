package io.github.nickacpt.patchify.core.tasks.impl

import io.github.nickacpt.patchify.core.git.Git
import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.core.tasks.AbstractPatchifyTask
import kotlin.io.path.name
import kotlin.io.path.useDirectoryEntries

class ApplyPatchesTask(workspace: PatchifyWorkspace) : AbstractPatchifyTask(workspace) {
    override fun run() {
        val patches = workspace.patchesDirectory.useDirectoryEntries { it.toList() }

        with(Git(workspace.sourceDirectory)) {
            patches.forEach { patchFile ->

                val isSuccess = applyPatch(patchFile) == 0

                if (isSuccess) {
                    println("Patch ${patchFile.name} applied successfully")
                } else {
                    println("Error occured while applying patch ${patchFile.name}")
                }
            }
        }
    }
}