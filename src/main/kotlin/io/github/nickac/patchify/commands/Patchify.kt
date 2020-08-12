package io.github.nickac.patchify.commands

import com.github.ajalt.clikt.core.NoOpCliktCommand

class Patchify : NoOpCliktCommand() {
    override fun aliases(): Map<String, List<String>> =
        mapOf(
            "rebuild" to listOf("rebuild-patches"),
            "rb" to listOf("rebuild-patches"),
            "rbp" to listOf("rebuild-patches"),
            "patch" to listOf("apply-patches"),
            "p" to listOf("apply-patches"),
            "e" to listOf("edit-patch")

        )
}

