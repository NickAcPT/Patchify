package io.github.nickac.patchify

import com.github.ajalt.clikt.core.subcommands
import io.github.nickac.patchify.commands.*

fun main(args: Array<String>) {
    Patchify().subcommands(
        Init(),
        RebuildPatches,
        ApplyPatches(),
        EditPatch(),
        Reset()
    ).main(args)
}