package io.github.nickac.patchify.commands

import io.github.nickac.patchify.extensions.currentDir
import io.github.nickac.patchify.extensions.findPatchifyFile
import kotlinx.serialization.Serializable
import org.eclipse.jgit.api.Git
import java.io.File

@Serializable
class PatchifyData {
    val sourcesGit: Git get() = Git.open(sourceDirectory)

    private val sourceDir: String
    private val patchesDir: String
    internal var lastEditCommit: String? = null

    val sourceDirectory get() = File(currentDir.findPatchifyFile().parentFile.canonicalFile, sourceDir)
    val patchesDirectory get() = File(currentDir.findPatchifyFile().parentFile.canonicalFile, patchesDir)

    constructor(sourceDir: File, patchesDir: File) {
        //Take the current dir and scan upwards to find the patchify file
        this.sourceDir = sourceDir.toRelativeString(currentDir)
        this.patchesDir = patchesDir.toRelativeString(currentDir)
    }

    constructor(sourceDir: String, patchesDir: String) {
        this.sourceDir = sourceDir
        this.patchesDir = patchesDir
    }
}