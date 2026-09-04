package com.solara.browser.extensions.splitview

data class SplitState(
    val isActive: Boolean = false,
    val primaryUrl: String = "",
    val secondaryUrl: String = ""
) {
    companion object {
        val EMPTY = SplitState()
    }
}

object SplitViewManager {

    private var state = SplitState.EMPTY

    fun getState(): SplitState = state

    fun activate(primaryUrl: String, secondaryUrl: String = "https://example.com") {
        state = SplitState(
            isActive = true,
            primaryUrl = primaryUrl,
            secondaryUrl = secondaryUrl
        )
    }

    fun deactivate() {
        state = SplitState.EMPTY
    }

    fun updateSecondary(url: String) {
        state = state.copy(secondaryUrl = url)
    }

    fun updatePrimary(url: String) {
        state = state.copy(primaryUrl = url)
    }

    fun isActive(): Boolean = state.isActive
}
