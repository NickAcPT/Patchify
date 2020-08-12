package io.github.nickac.patchify.extensions

import java.io.File

const val patchifyFile = ".patchify"

const val patchesDir = "patches"
const val sourceDir = "source"

const val gitIgnore = ".gitignore"
const val initTagName = "init"
const val initTagRef = "refs/tags/$initTagName"

const val errDirNotManaged = "You must be on a directory managed by Patchify"
val currentDir = File(".")