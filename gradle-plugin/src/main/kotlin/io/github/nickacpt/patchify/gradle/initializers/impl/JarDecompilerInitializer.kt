package io.github.nickacpt.patchify.gradle.initializers.impl

import io.github.nickacpt.patchify.core.model.PatchifyWorkspace
import io.github.nickacpt.patchify.gradle.initializers.WorkspaceInitializer
import org.jetbrains.java.decompiler.main.Fernflower
import org.jetbrains.java.decompiler.main.decompiler.DirectoryResultSaver
import org.jetbrains.java.decompiler.main.decompiler.PrintStreamLogger
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider
import org.jetbrains.java.decompiler.util.InterpreterUtil
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.util.zip.ZipFile


data class JarDecompilerInitializer(val jarToDecompile: Path, var outputRoot: Path) : WorkspaceInitializer,
    IBytecodeProvider {

    override fun initialize(workspace: PatchifyWorkspace) {
        val decompiler = Fernflower(
            this,
            DirectoryResultSaver(workspace.sourceDirectory.resolve(outputRoot).toFile()),
            emptyMap(),
            PrintStreamLogger(System.out)
        )

        decompiler.addSource(jarToDecompile.toFile())
        try {
            decompiler.decompileContext()
        } finally {
            decompiler.clearContext()
        }
    }

    override fun getBytecode(externalPath: String, internalPath: String?): ByteArray {
        val file = File(externalPath)
        if (internalPath == null) {
            return InterpreterUtil.getBytes(file)
        } else {
            ZipFile(file).use { archive ->
                val entry =
                    archive.getEntry(internalPath) ?: throw IOException("Entry not found: $internalPath")
                return InterpreterUtil.getBytes(archive, entry)
            }
        }
    }

}
