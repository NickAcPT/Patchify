package io.github.nickacpt.patchify.gradle.utils

import io.github.nickacpt.patchify.gradle.extension.PatchifyExtension
import org.gradle.api.Project

val Project.patchifyExtension: PatchifyExtension
    get() = this.extensions.getByType(PatchifyExtension::class.java)
