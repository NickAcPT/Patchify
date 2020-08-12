package io.github.nickac.patchify.gitignore

import java.net.URL

object GitIgnoreFetcher {
    private const val baseUrl = "https://www.toptal.com/developers/gitignore/api/{types}"

    fun fetch(types: List<GitIgnoreType>): String {
        if (types.isEmpty()) return ""

        return URL(baseUrl.replace("{types}", types.joinToString(",") { it.id })).openConnection().apply {
            setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36"
            )
        }.getInputStream().use { it.readBytes() }.toString(
            Charsets.UTF_8
        )
    }

}