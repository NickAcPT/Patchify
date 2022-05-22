package io.github.nickacpt.patchify.cli.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import java.nio.file.Path
import kotlin.io.path.pathString

data class WorkspaceDefinition(
    val sourceDir: String,
    val patchesDir: String,
) {
    companion object {
        private val mapper = jacksonObjectMapper()

        fun fromJson(json: String): WorkspaceDefinition {
            return mapper.readValue(json, WorkspaceDefinition::class.java)
        }

        fun toJson(workspace: WorkspaceDefinition): String {
            return mapper.writeValueAsString(workspace)
        }

        fun fromWorkspace(workspace: PatchifyWorkspace, currentPath: Path): WorkspaceDefinition {
            return WorkspaceDefinition(
                sourceDir = currentPath.relativize(workspace.sourceDirectory).pathString,
                patchesDir = currentPath.relativize(workspace.patchesDirectory).pathString
            )
        }

        fun toWorkspace(definition: WorkspaceDefinition, currentPath: Path): PatchifyWorkspace {
            return PatchifyWorkspace(
                sourceDirectory = currentPath.resolve(definition.sourceDir),
                patchesDirectory = currentPath.resolve(definition.patchesDir)
            )
        }
    }
}
