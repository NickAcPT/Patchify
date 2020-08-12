package io.github.nickac.patchify.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.github.nickac.patchify.extensions.currentDir
import io.github.nickac.patchify.extensions.errDirNotManaged
import io.github.nickac.patchify.extensions.patchifyData

class ApplyPatches : CliktCommand("Apply all patches to the project.") {
    override fun run() {
        val data = checkNotNull(currentDir.patchifyData, { errDirNotManaged })

        val files = data.patchesDirectory.listFiles() ?: return
        files.forEach {

            //Invoke git since jGit doesn't support am
            val isSuccess = ProcessBuilder()
                .command(
                    "git",
                    "am",
                    "\"${it.absolutePath}\"",
                    "--keep-cr"
                )
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .directory(data.sourceDirectory.canonicalFile)
                .start().waitFor() == 0

            if (isSuccess)
                echo("Patch ${it.name} applied successfully.")
            else
                echo("Error occured while applying patch ${it.name}.")

        }
    }
}