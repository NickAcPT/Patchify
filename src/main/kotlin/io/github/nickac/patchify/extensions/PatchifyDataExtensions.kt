package io.github.nickac.patchify.extensions

import io.github.nickac.patchify.commands.PatchifyData
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File

private val json = Json(JsonConfiguration.Stable)

var File.patchifyData: PatchifyData?
    get() {
        return findPatchifyFile().takeIf { it.exists() }
            ?.let {
                json.parse(
                    PatchifyData.serializer(), it.readText()
                )
            }
    }
    set(value) {
        findPatchifyFile().let {
            if (value != null) {
                it.writeText(json.stringify(PatchifyData.serializer(), value))
            } else {
                it.delete()
            }

        }
    }

fun File.findPatchifyFile(recursiveScan: Boolean = true): File =
    File(this, patchifyFile).takeIf { !recursiveScan || it.exists() } ?: this.canonicalFile.parentFile.findPatchifyFile()
