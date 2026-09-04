package com.solara.browser.extensions.reader

data class ReaderContent(
    val title: String,
    val body: String,
    val sourceUrl: String
)

object ReaderMode {

    private val nonContentSelectors = listOf(
        "nav", "header", "footer", "aside",
        ".sidebar", ".nav", ".menu", ".header", ".footer",
        ".ad", ".ads", ".advertisement", ".social",
        ".share", ".comments", ".comment", ".related",
        ".newsletter", ".subscribe", ".popup", ".modal"
    )

    fun extractContent(html: String, url: String): ReaderContent {
        val title = extractTag(html, "title") ?: url
        val body = extractBody(html)
        return ReaderContent(
            title = title,
            body = body,
            sourceUrl = url
        )
    }

    private fun extractTag(html: String, tag: String): String? {
        val regex = Regex("<$tag[^>]*>(.*?)</$tag>", RegexOption.DOT_MATCHES_ALL)
        return regex.find(html)?.groupValues?.get(1)?.trim()
    }

    private fun extractBody(html: String): String {
        val bodyRegex = Regex("<body[^>]*>(.*?)</body>", RegexOption.DOT_MATCHES_ALL)
        val body = bodyRegex.find(html)?.groupValues?.get(1) ?: html
        val textOnly = body.replace(Regex("<script[^>]*>.*?</script>", RegexOption.DOT_MATCHES_ALL), "")
            .replace(Regex("<style[^>]*>.*?</style>", RegexOption.DOT_MATCHES_ALL), "")
            .replace(Regex("<[^>]+>"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
        return textOnly
    }

    fun isReadableUrl(url: String): Boolean {
        val readableDomains = listOf(
            "medium.com", "dev.to", "arstechnica.com", "theverge.com",
            "wired.com", "bbc.com", "nytimes.com", "theguardian.com",
            "wikipedia.org", "docs.", "github.com"
        )
        return readableDomains.any { url.lowercase().contains(it) }
    }
}
