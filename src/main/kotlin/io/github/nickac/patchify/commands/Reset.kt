package io.github.nickac.patchify.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.github.nickac.patchify.extensions.currentDir
import io.github.nickac.patchify.extensions.initTagRef
import io.github.nickac.patchify.extensions.patchifyData
import org.eclipse.jgit.api.ResetCommand

class Reset : CliktCommand("Resets a modified git repository to the original source-code") {

    override fun run() {
        val patchifyData = currentDir.patchifyData ?: return

        patchifyData.sourcesGit?.reset()
            ?.setMode(ResetCommand.ResetType.HARD)
            ?.setRef(initTagRef)
            ?.call()

    }
}