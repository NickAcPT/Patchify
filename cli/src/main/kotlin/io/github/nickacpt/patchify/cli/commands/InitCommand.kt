package io.github.nickacpt.patchify.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import io.github.nickacpt.patchify.cli.WorkspaceUtils.currentPath
import io.github.nickacpt.patchify.cli.WorkspaceUtils.writeWorkspace
import io.github.nickacpt.patchify.cli.model.WorkspaceDefinition
import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.core.tasks.impl.InitSourceDirectoryTask

object InitCommand : CliktCommand(
    help = "Initialize a new patchify workspace"
) {
    private val sourcesDir by argument(
        help = "The directory to store the sources to patch"
    ).path(canBeDir = true, canBeFile = false)

    private val patchesDir by argument(
        help = "The directory to store the patches to apply"
    ).path(canBeDir = true, canBeFile = false)

    private val force by option("-f", "--force", help = "Force initialization of an existing workspace repo").flag()

    override fun run() {
        // Initialize the workspace
        val workspace = PatchifyWorkspace(sourcesDir, patchesDir)
        InitSourceDirectoryTask(workspace, force).run()

        // Write the workspace definition to the current directory to keep track of the workspace in future commands
        val definition = WorkspaceDefinition.fromWorkspace(workspace, currentPath)
        writeWorkspace(currentPath, definition)

        echo("Initialized patchify workspace successfully.")
    }
}