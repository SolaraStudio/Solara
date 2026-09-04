package com.solara.browser.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "solara_settings")

class SettingsRepository(private val context: Context) {

    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
        val AD_BLOCKER_ENABLED = booleanPreferencesKey("ad_blocker_enabled")
        val TRACKER_BLOCKING = booleanPreferencesKey("tracker_blocking")
        val HTTPS_ONLY = booleanPreferencesKey("https_only")
        val COMPACT_MODE = booleanPreferencesKey("compact_mode")
        val READER_MODE = booleanPreferencesKey("reader_mode")
        val HOME_PAGE_URL = stringPreferencesKey("home_page_url")
        val SEARCH_ENGINE = stringPreferencesKey("search_engine")
        val FONT_SIZE = intPreferencesKey("font_size")
        val SWIPE_NAVIGATION = booleanPreferencesKey("swipe_navigation")
        val BIOMETRIC_LOCK = booleanPreferencesKey("biometric_lock")
        val CLEAR_ON_EXIT = booleanPreferencesKey("clear_on_exit")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { it[Keys.DARK_MODE] ?: true }
    val isDynamicColor: Flow<Boolean> = context.dataStore.data.map { it[Keys.DYNAMIC_COLOR] ?: true }
    val isAdBlockerEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.AD_BLOCKER_ENABLED] ?: true }
    val isTrackerBlocking: Flow<Boolean> = context.dataStore.data.map { it[Keys.TRACKER_BLOCKING] ?: true }
    val isHttpsOnly: Flow<Boolean> = context.dataStore.data.map { it[Keys.HTTPS_ONLY] ?: false }
    val isCompactMode: Flow<Boolean> = context.dataStore.data.map { it[Keys.COMPACT_MODE] ?: false }
    val isReaderMode: Flow<Boolean> = context.dataStore.data.map { it[Keys.READER_MODE] ?: false }
    val homePageUrl: Flow<String> = context.dataStore.data.map { it[Keys.HOME_PAGE_URL] ?: "solara://home" }
    val searchEngine: Flow<String> = context.dataStore.data.map { it[Keys.SEARCH_ENGINE] ?: "google" }
    val fontSize: Flow<Int> = context.dataStore.data.map { it[Keys.FONT_SIZE] ?: 16 }
    val isSwipeNavigation: Flow<Boolean> = context.dataStore.data.map { it[Keys.SWIPE_NAVIGATION] ?: true }
    val isBiometricLock: Flow<Boolean> = context.dataStore.data.map { it[Keys.BIOMETRIC_LOCK] ?: false }
    val isClearOnExit: Flow<Boolean> = context.dataStore.data.map { it[Keys.CLEAR_ON_EXIT] ?: false }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setDynamicColor(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DYNAMIC_COLOR] = enabled }
    }

    suspend fun setAdBlocker(enabled: Boolean) {
        context.dataStore.edit { it[Keys.AD_BLOCKER_ENABLED] = enabled }
    }

    suspend fun setTrackerBlocking(enabled: Boolean) {
        context.dataStore.edit { it[Keys.TRACKER_BLOCKING] = enabled }
    }

    suspend fun setHttpsOnly(enabled: Boolean) {
        context.dataStore.edit { it[Keys.HTTPS_ONLY] = enabled }
    }

    suspend fun setCompactMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.COMPACT_MODE] = enabled }
    }

    suspend fun setReaderMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.READER_MODE] = enabled }
    }

    suspend fun setHomePageUrl(url: String) {
        context.dataStore.edit { it[Keys.HOME_PAGE_URL] = url }
    }

    suspend fun setSearchEngine(engine: String) {
        context.dataStore.edit { it[Keys.SEARCH_ENGINE] = engine }
    }

    suspend fun setFontSize(size: Int) {
        context.dataStore.edit { it[Keys.FONT_SIZE] = size }
    }

    suspend fun setSwipeNavigation(enabled: Boolean) {
        context.dataStore.edit { it[Keys.SWIPE_NAVIGATION] = enabled }
    }

    suspend fun setBiometricLock(enabled: Boolean) {
        context.dataStore.edit { it[Keys.BIOMETRIC_LOCK] = enabled }
    }

    suspend fun setClearOnExit(enabled: Boolean) {
        context.dataStore.edit { it[Keys.CLEAR_ON_EXIT] = enabled }
    }

    fun getSearchUrl(query: String): String {
        val engine = run { context.dataStore.data.map { it[Keys.SEARCH_ENGINE] ?: "google" } }
        return "https://www.google.com/search?q=$query"
    }
}
