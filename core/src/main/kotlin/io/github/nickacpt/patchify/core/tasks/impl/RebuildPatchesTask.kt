package io.github.nickacpt.patchify.core.tasks.impl

import io.github.nickacpt.patchify.core.PatchifyConstants
import io.github.nickacpt.patchify.core.git.Git
import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.core.tasks.AbstractPatchifyTask
import java.nio.file.Files
import kotlin.io.path.useDirectoryEntries

class RebuildPatchesTask(workspace: PatchifyWorkspace) : AbstractPatchifyTask(workspace) {

    override fun run() {
        // First, we either need to create the patches directory or delete the contents of it if it exists
        if (Files.notExists(workspace.patchesDirectory)) {
            Files.createDirectories(workspace.patchesDirectory)
        } else {
            workspace.patchesDirectory.useDirectoryEntries { file -> file.forEach { Files.delete(it) } }
        }

        // Generate patches from the current state of the workspace
        with(Git(workspace.sourceDirectory)) {
            formatPatch(PatchifyConstants.INITIAL_TAG, workspace.patchesDirectory)
        }

        // Then add the patches for the user to commit later
        with(Git(workspace.patchesDirectory)) {
            add(".", silent = true)
        }
    }
}