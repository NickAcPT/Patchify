package io.github.nickac.patchify.extensions

import org.eclipse.jgit.api.Git
import java.io.File

private val File?.repository: org.eclipse.jgit.lib.Repository?
    get() {
        return try {
            this?.apply { mkdirs() }?.let { Git.open(it)?.repository }
        } catch (e: Exception) {
            null
        }
    }