package com.solara.browser.extensions.adblock

object AdBlocker {

    private val adDomains = setOf(
        "doubleclick.net",
        "googlesyndication.com",
        "googleadservices.com",
        "adnxs.com",
        "adsrvr.org",
        "amazon-adsystem.com",
        "casalemedia.com",
        "chartbeat.com",
        "criteo.com",
        "criteo.net",
        "facebook.net",
        "hotjar.com",
        "taboola.com",
        "twitter.com",
        "pubmatic.com",
        "rubiconproject.com",
        "serving-sys.com",
        "spotxchange.com",
        "yieldmo.com",
        "moatads.com",
        "quantserve.com",
        "scorecardresearch.com",
        "bluekai.com",
        "demdex.net",
        "everesttech.net",
        "liadm.com",
        "sharethrough.com",
        "sonobi.com",
        "spotx.tv",
        "adingo.jp",
        "adnxs.com",
        "adskeeper.com",
        "advertising.com",
        "bidswitch.net",
        "connatix.com",
        "contextweb.com",
        "crwdcntrl.net",
        "media.net",
        "teads.tv",
        "outbrain.com",
        "zemanta.com"
    )

    private val adPathPatterns = listOf(
        "/ad/",
        "/ads/",
        "/advert/",
        "/banner/",
        "/popup/",
        "/popunder/",
        "/track/",
        "/pixel/",
        "/beacon/",
        "/analytics/"
    )

    fun isAdUrl(url: String): Boolean {
        val lower = url.lowercase()
        return adDomains.any { domain -> lower.contains(domain) } ||
            adPathPatterns.any { pattern -> lower.contains(pattern) }
    }

    fun shouldBlock(url: String, enabled: Boolean): Boolean {
        if (!enabled) return false
        return isAdUrl(url)
    }
}
