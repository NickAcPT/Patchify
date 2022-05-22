package io.github.nickacpt.patchify.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import io.github.nickacpt.patchify.cli.commands.ApplyPatchesCommand
import io.github.nickacpt.patchify.cli.commands.InitCommand
import io.github.nickacpt.patchify.cli.commands.RebuildPatchesCommand

class Entrypoint : CliktCommand(
    name = "patchify",
    help = "Patchify is a tool used to manage/automate code projects that require source-code patching."
) {
    init {
        subcommands(InitCommand, ApplyPatchesCommand, RebuildPatchesCommand)
    }

    override fun run() = Unit
}

fun main(args: Array<String>) {
    Entrypoint().main(args)
}