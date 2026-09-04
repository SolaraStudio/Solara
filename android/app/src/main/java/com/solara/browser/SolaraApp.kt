package com.solara.browser

import android.app.Application
import com.solara.browser.data.local.SolaraDatabase
import com.solara.browser.data.repository.BookmarkRepository
import com.solara.browser.data.repository.DownloadRepository
import com.solara.browser.data.repository.HistoryRepository
import com.solara.browser.data.repository.SettingsRepository
import com.solara.browser.data.repository.TabRepository
import com.solara.browser.data.repository.WorkspaceRepository

class SolaraApp : Application() {

    val database by lazy { SolaraDatabase.getInstance(this) }

    val tabRepository by lazy { TabRepository(database.tabDao()) }
    val workspaceRepository by lazy { WorkspaceRepository(database.workspaceDao()) }
    val bookmarkRepository by lazy { BookmarkRepository(database.bookmarkDao()) }
    val historyRepository by lazy { HistoryRepository(database.historyDao()) }
    val downloadRepository by lazy { DownloadRepository(database.downloadDao()) }
    val settingsRepository by lazy { SettingsRepository(this) }

    override fun onCreate() {
        super.onCreate()
        kotlinx.coroutines.MainScope().launch {
            workspaceRepository.ensureDefaultWorkspace()
        }
    }
}
