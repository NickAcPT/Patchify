package io.github.nickacpt.patchify.gradle.model.builders

import io.github.nickacpt.patchify.gradle.initializers.WorkspaceInitializer
import io.github.nickacpt.patchify.gradle.initializers.impl.EmptyWorkspaceInitializer
import io.github.nickacpt.patchify.gradle.model.PatchifyGradleWorkspace
import java.nio.file.Path

class PatchifyWorkspaceBuilder {
    internal var name: String? = null
    lateinit var source: Path
    lateinit var patches: Path
    private var initializer: WorkspaceInitializer? = null

    fun initializer(builder: WorkspaceInitializerBuilder.() -> Unit) {
        initializer = WorkspaceInitializerBuilder().apply(builder).initializer
    }

    internal fun build(): PatchifyGradleWorkspace {
        return PatchifyGradleWorkspace(name ?: throw Exception("Workspace name is missing"), source, patches, initializer ?: EmptyWorkspaceInitializer)
    }

}