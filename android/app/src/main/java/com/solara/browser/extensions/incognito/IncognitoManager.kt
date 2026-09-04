package com.solara.browser.extensions.incognito

object IncognitoManager {

    private var isIncognitoActive = false
    private val incognitoTabs = mutableListOf<String>()

    fun isActive(): Boolean = isIncognitoActive

    fun activate() {
        isIncognitoActive = true
    }

    fun deactivate() {
        isIncognitoActive = false
        incognitoTabs.clear()
    }

    fun addTab(tabId: String) {
        if (isIncognitoActive) {
            incognitoTabs.add(tabId)
        }
    }

    fun removeTab(tabId: String) {
        incognitoTabs.remove(tabId)
    }

    fun getTabs(): List<String> = incognitoTabs.toList()

    fun clearTabs() {
        incognitoTabs.clear()
    }
}
