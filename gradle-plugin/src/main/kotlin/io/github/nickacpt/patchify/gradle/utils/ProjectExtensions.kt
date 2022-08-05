package io.github.nickacpt.patchify.gradle.utils

import io.github.nickacpt.patchify.gradle.extension.PatchifyExtension
import io.github.nickacpt.patchify.gradle.model.PatchifyGradleWorkspace
import io.github.nickacpt.patchify.gradle.tasks.PatchifyWorkspaceTask
import org.gradle.api.Project

val Project.patchifyExtension: PatchifyExtension
    get() = this.extensions.getByType(PatchifyExtension::class.java)


internal fun Project.registerWorkspaceTask(name: String, workspace: PatchifyGradleWorkspace, taskClazz: Class<out PatchifyWorkspaceTask>) =
    tasks.register(name, taskClazz, workspace)
