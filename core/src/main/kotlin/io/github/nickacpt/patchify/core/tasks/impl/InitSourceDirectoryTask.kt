package io.github.nickacpt.patchify.core.tasks.impl

import io.github.nickacpt.patchify.core.PatchifyConstants
import io.github.nickacpt.patchify.core.git.Git
import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.core.tasks.AbstractPatchifyTask
import java.nio.file.Files

class InitSourceDirectoryTask(workspace: PatchifyWorkspace, private val forceInit: Boolean = false) : AbstractPatchifyTask(workspace) {

    override fun run() {
        // Make sure the source directory exists
        if (Files.notExists(workspace.sourceDirectory)) {
            Files.createDirectories(workspace.sourceDirectory)
        }

        with(Git(workspace.sourceDirectory)) {
            // Initialize the git repo if it doesn't exist
            if (!forceInit && isInitialized()) {
                // Do nothing as everything is already initialized and ready to go
                return
            }

            // Initialize the git repo
            init()

            // Then add all files to the repo
            add(".", silent = true)

            // And commit the changes
            commit("Initial commit", silent = true)

            // Then tag the commit as the initial version
            tag(PatchifyConstants.INITIAL_TAG, silent = true)
        }
    }
}