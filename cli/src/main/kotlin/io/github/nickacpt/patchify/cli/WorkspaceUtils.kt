package io.github.nickacpt.patchify.cli

import io.github.nickacpt.patchify.cli.model.WorkspaceDefinition
import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.pathString

object WorkspaceUtils {
    val currentPath: Path by lazy { FileSystems.getDefault().getPath(".") }

    private const val WORKSPACE_FILE_NAME = ".patchify"

    private fun getWorkspaceDefinitionPath(currentPath: Path): Path {
        return currentPath.resolve(WORKSPACE_FILE_NAME)
    }

    fun writeWorkspace(currentPath: Path, definition: WorkspaceDefinition) {
        val definitionPath = getWorkspaceDefinitionPath(currentPath)
        Files.writeString(definitionPath, WorkspaceDefinition.toJson(definition))
    }

    fun getWorkspaceOrThrow(currentPath: Path): PatchifyWorkspace {
        val definitionPath = getWorkspaceDefinitionPath(currentPath)
        if (!Files.exists(definitionPath)) {
            throw IllegalStateException("Unable to find workspace at ${definitionPath.pathString}")
        }

        val definition = WorkspaceDefinition.fromJson(Files.readString(definitionPath))
        return WorkspaceDefinition.toWorkspace(definition, currentPath)
    }
}