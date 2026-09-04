package com.solara.browser.extensions.adblock

object TrackerBlocker {

    private val trackerDomains = setOf(
        "google-analytics.com",
        "googletagmanager.com",
        "googletagservices.com",
        "facebook.com/tr",
        "facebook.net/nca",
        "hotjar.com",
        "mixpanel.com",
        "segment.io",
        "segment.com",
        "amplitude.com",
        "branch.io",
        "adjust.com",
        "appsflyer.com",
        "instabug.com",
        "bugsnag.com",
        "sentry.io",
        "newrelic.com",
        "chartbeat.com",
        "mouseflow.com",
        "crazyegg.com",
        "luckyorange.com",
        "optimizely.com",
        "fullstory.com",
        "heap.io",
        "matomo.org",
        "piwik.com",
        "plausible.io",
        "umami.is",
        "simpleanalytics.com"
    )

    fun isTrackerUrl(url: String): Boolean {
        val lower = url.lowercase()
        return trackerDomains.any { domain -> lower.contains(domain) }
    }

    fun shouldBlock(url: String, enabled: Boolean): Boolean {
        if (!enabled) return false
        return isTrackerUrl(url)
    }
}
