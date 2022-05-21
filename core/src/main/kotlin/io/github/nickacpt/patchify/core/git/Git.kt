package io.github.nickacpt.patchify.core.git

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class Git(private val directory: Path) {

    companion object {
        // Internal variable to keep track if we have git available to us
        internal var isGitAvailable = false
    }

    init {
        ensureGitInstalled()
    }

    private fun ensureGitInstalled() {
        if (isGitAvailable) return

        // Check if git is installed by running a simple command
        isGitAvailable = execute("git", "--version", silent = true) == 0

        // If git is not installed, throw an exception
        if (!isGitAvailable) throw IllegalStateException("Git is not in PATH. Make sure git is installed and in PATH before using Patchify.")
    }

    fun isInitialized(): Boolean {
        return Files.exists(directory.resolve(".git"))
    }

    fun init() {
        execute("git", "init")
    }

    fun add(path: String, silent: Boolean = false) {
        execute("git", "add", "-A", path, silent = silent)
    }

    fun commit(message: String, silent: Boolean = false) {
        execute("git", "commit", "-m", message, silent = silent)
    }

    fun formatPatch(initial: String, outputDir: Path) {
        execute(
            "git",
            "format-patch",
            "$initial..HEAD",
            "--minimal",
            "--full-index",
            "--no-stat",
            "-N",
            "-o",
            outputDir.absolutePathString(),
            silent = true
        )
    }

    fun tag(tag: String, delete: Boolean = false, silent: Boolean = false) {
        execute(
            *if (delete) {
                arrayOf("git", "tag", "-d", tag)
            } else {
                arrayOf("git", "tag", tag)
            }, silent = silent
        )
    }

    private fun execute(vararg arguments: String, silent: Boolean = false): Int {
        val cleanedArgs = arguments.map { arg -> if (arg.any(Char::isWhitespace)) "'$arg'" else arg }

        try {
            println("Executing: ${cleanedArgs.joinToString(" ")}")
            return ProcessBuilder(*cleanedArgs.toTypedArray())
                .directory(directory.toFile())
                .also { if (!silent) it.inheritIO() }
                .start().waitFor()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to execute git command: ${cleanedArgs.joinToString(" ")}", e)
        }
    }

}