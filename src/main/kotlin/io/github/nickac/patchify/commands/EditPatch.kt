package io.github.nickac.patchify.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import io.github.nickac.patchify.extensions.currentDir
import io.github.nickac.patchify.extensions.errDirNotManaged
import io.github.nickac.patchify.extensions.initTagRef
import io.github.nickac.patchify.extensions.patchifyData
import org.eclipse.jgit.api.RebaseCommand
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.RebaseTodoLine

class EditPatch : CliktCommand("Edits a patch") {
    val patchId by argument().int().optional()
    val continuePatch by option("--continue").flag()
    val abortPatch by option("--abort").flag()

    override fun run() {
        val data = checkNotNull(currentDir.patchifyData, { errDirNotManaged })
        val git = data.sourcesGit ?: return

        if (abortPatch) {
            git.rebase().setOperation(RebaseCommand.Operation.ABORT).call()
            echo("The rebase has been aborted")
        } else if (!continuePatch) {
            val commitList = git.log().addRange(
                git.repository.parseCommit(git.repository.refDatabase.peel(git.repository.refDatabase.findRef(initTagRef)).peeledObjectId)
                    .getParent(0),
                git.repository.resolve("HEAD")
            ).call().toList()

            val toEdit =
                commitList.reversed()[patchId ?: 1]

            currentDir.patchifyData = data.apply { lastEditCommit = toEdit.toObjectId().name }

            // We're starting the rebase freshly
            git.rebase().runInteractively(object : RebaseCommand.InteractiveHandler {
                override fun prepareSteps(steps: MutableList<RebaseTodoLine>) {
                    steps.forEach {
                        if (it.commit.prefixCompare(toEdit.toObjectId()) == 0) {
                            it.action = RebaseTodoLine.Action.EDIT
                        }
                    }
                }

                override fun modifyCommitMessage(commit: String): String {
                    return commit
                }
            }).setUpstream(toEdit.getParent(0)).call()

        } else {
            git.add()
            ProcessBuilder()
                .command(
                    "git",
                    "add",
                    "."
                )
                .directory(data.sourceDirectory.canonicalFile).start()
            git.commit()
                .apply { message = git.repository.parseCommit(ObjectId.fromString(data.lastEditCommit)).fullMessage }
                .setAmend(true).call()
            git.rebase().setOperation(RebaseCommand.Operation.CONTINUE).call()

            currentDir.patchifyData = data.apply { lastEditCommit = null }
            echo("Patch edit has succeeded. Rebuilding patches.")
            RebuildPatches.run()
        }

    }
}