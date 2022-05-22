package io.github.nickacpt.patchify.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.github.nickacpt.patchify.cli.WorkspaceUtils
import io.github.nickacpt.patchify.cli.WorkspaceUtils.currentPath
import io.github.nickacpt.patchify.core.tasks.impl.ApplyPatchesTask

object ApplyPatchesCommand : CliktCommand(name = "apply-patches", help = "Apply all patches to the source code") {
    override fun run() {
        val workspace = WorkspaceUtils.getWorkspaceOrThrow(currentPath)

        ApplyPatchesTask(workspace).run()
    }
}