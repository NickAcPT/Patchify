package io.github.nickacpt.patchify.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.github.nickacpt.patchify.cli.WorkspaceUtils
import io.github.nickacpt.patchify.cli.WorkspaceUtils.currentPath
import io.github.nickacpt.patchify.core.tasks.impl.RebuildPatchesTask

object RebuildPatchesCommand : CliktCommand(name = "rebuild-patches", help = "Rebuilds all patches") {
    override fun run() {
        val workspace = WorkspaceUtils.getWorkspaceOrThrow(currentPath)

        RebuildPatchesTask(workspace).run()
    }
}