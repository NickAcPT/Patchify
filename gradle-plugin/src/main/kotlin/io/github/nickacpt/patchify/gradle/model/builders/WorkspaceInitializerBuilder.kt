package io.github.nickacpt.patchify.gradle.model.builders

import io.github.nickacpt.patchify.gradle.initializers.WorkspaceInitializer
import io.github.nickacpt.patchify.gradle.initializers.impl.JarDecompilerInitializer
import java.io.File
import java.nio.file.Path

class WorkspaceInitializerBuilder {
    internal var initializer: WorkspaceInitializer? = null

    fun initializer(initializer: WorkspaceInitializer) {
        this.initializer = initializer
    }

    fun decompileJar(jar: File, sourceRoot: String?) = decompileJar(jar.toPath(), sourceRoot)

    fun decompileJar(jar: Path, sourceRoot: String?) {
        initializer(JarDecompilerInitializer(jar, Path.of(sourceRoot ?: ".")))
    }


}