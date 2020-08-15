package io.github.nickac.patchify.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import io.github.nickac.patchify.extensions.*
import io.github.nickac.patchify.gitignore.GitIgnoreFetcher
import io.github.nickac.patchify.gitignore.GitIgnoreType
import org.eclipse.jgit.api.Git
import java.io.File

class Init : CliktCommand("Initializes a git repository to be able to patch source-code") {
    private val sourcesDirectory by option("--sourcesDir", "--srcDir", "--sourceDir").file(canBeFile = false)
    private val gitIgnoreTypes by argument().enum<GitIgnoreType>().multiple().optional()
    private val gitRepo by option("--gitRepo")
    private val patchesDirectory by option("--patchDir", "--patchesDir").file(canBeFile = false)

    override fun run() {
        val patchifyDataFile = currentDir.findPatchifyFile(false)
        val isPatchify = patchifyDataFile.exists()
        val sourcesDir = sourcesDirectory ?: File(
            currentDir,
            sourceDir
        )
        val patchesDir = patchesDirectory ?: File(
            currentDir,
            patchesDir
        )

        //Delete source directory if not patchify and exists
        if (!isPatchify) sourcesDir.takeIf { it.exists() }?.deleteRecursively()

        var repo: Git? = null

        val wantsExistingGitRepo = gitRepo != null
        if (wantsExistingGitRepo) {
            //We can add this as a sub-module

            Git.open(patchifyDataFile.parentFile)
                .use {
                    repo = Git(it.submoduleAdd()
                        .setURI(gitRepo)
                        .setPath(sourcesDir.toPath().normalize().toString())
                        .call()
                    )
                }

        } else {
            //Code needs to be manually set-up by the user
            repo = if (isPatchify) Git.open(sourcesDir) else Git.init()
                .setDirectory(sourcesDir).setBare(false).call()
        }


        if (!wantsExistingGitRepo && !isPatchify) {
            gitIgnoreTypes?.let { GitIgnoreFetcher.fetch(it) }?.let {
                File(
                    sourcesDir,
                    gitIgnore
                ).writeText(it)
            }

            echo("Base files created.")
            echo("Add the source-code to be patched and run the command again.")
        } else {
            if (repo != null) {
                if (!wantsExistingGitRepo) {
                    repo!!.add().addFilepattern(".").call()
                    repo!!.commit().setMessage("Initial Commit").call()
                }
                repo!!.tag().setName(initTagName).call()

                echo("Source code processed.")
                echo("You can now proceed with changing the source-code.")
            }
        }

        if (!isPatchify) {
            patchifyDataFile.createNewFile()
            currentDir.patchifyData =
                PatchifyData(sourcesDir, patchesDir)
        }
    }
}