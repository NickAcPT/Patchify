package io.github.nickacpt.patchify.core.tasks.impl

import io.github.nickacpt.patchify.core.git.Git
import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.core.tasks.AbstractPatchifyTask
import java.nio.file.Files.createTempDirectory
import kotlin.io.path.*

class ApplyPatchesTask(workspace: PatchifyWorkspace) : AbstractPatchifyTask(workspace) {
    override fun run() {
        with(Git(workspace.sourceDirectory)) {
            abortMailbox()

            // Create temporary directory for patches
            val tempPatchesDir = createTempDirectory("patchify-mailbox").createDirectories()
            val newDir = tempPatchesDir.resolve("new").createDirectory()

            // Copy all patches to temporary directory
            workspace.patchesDirectory.forEachDirectoryEntry {
                it.copyTo(newDir.resolve(it.name), overwrite = true)
            }

            // Apply patches to source directory
            if (applyPatch(tempPatchesDir) == 0) {
                println("Patches applied successfully")
            }

            // Delete temporary directory
            tempPatchesDir.toFile().deleteRecursively()
        }
    }
}