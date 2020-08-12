package io.github.nickac.patchify.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.github.nickac.patchify.extensions.currentDir
import io.github.nickac.patchify.extensions.errDirNotManaged
import io.github.nickac.patchify.extensions.initTagName
import io.github.nickac.patchify.extensions.patchifyData
import org.eclipse.jgit.api.Git

object RebuildPatches : CliktCommand("Rebuild patches.") {

    override fun run() {
        val data = checkNotNull(currentDir.patchifyData, { errDirNotManaged })
        data.patchesDirectory.apply { deleteRecursively() }.mkdirs()

        //Invoke git since jGit doesn't support format-patch
        val isSuccess = ProcessBuilder()
            .command(
                "git",
                "format-patch",
                "$initTagName..HEAD",
                "--minimal",
                "-o",
                "\"${data.patchesDirectory.absolutePath}\""
            )
            .directory(data.sourceDirectory.canonicalFile)
            .start().waitFor() == 0

        echo(if (isSuccess) "Patches created successfully." else "An error occurred while generating the patches.")
    }
}