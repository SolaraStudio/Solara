package com.solara.browser.data.repository

import com.solara.browser.data.local.TabDao
import com.solara.browser.data.model.TabEntity
import com.solara.browser.data.model.TabInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class TabRepository(private val tabDao: TabDao) {

    fun getTabsByWorkspace(workspaceId: String): Flow<List<TabInfo>> {
        return tabDao.getTabsByWorkspace(workspaceId).map { entities ->
            entities.map { TabInfo.fromEntity(it) }
        }
    }

    fun getIncognitoTabs(): Flow<List<TabInfo>> {
        return tabDao.getIncognitoTabs().map { entities ->
            entities.map { TabInfo.fromEntity(it) }
        }
    }

    suspend fun getActiveTab(workspaceId: String): TabInfo? {
        return tabDao.getActiveTab(workspaceId)?.let { TabInfo.fromEntity(it) }
    }

    suspend fun getTabById(tabId: String): TabInfo? {
        return tabDao.getTabById(tabId)?.let { TabInfo.fromEntity(it) }
    }

    suspend fun createTab(
        workspaceId: String = "default",
        url: String = "about:blank",
        title: String = "New Tab",
        isActive: Boolean = true,
        isIncognito: Boolean = false
    ): TabInfo {
        val id = UUID.randomUUID().toString()
        val tab = TabEntity(
            id = id,
            workspaceId = workspaceId,
            title = title,
            url = url,
            isActive = isActive,
            isIncognito = isIncognito
        )
        if (isActive) {
            tabDao.deactivateAllTabs(workspaceId)
        }
        tabDao.insertTab(tab)
        return TabInfo.fromEntity(tab)
    }

    suspend fun selectTab(tabId: String, workspaceId: String) {
        tabDao.deactivateAllTabs(workspaceId)
        val tab = tabDao.getTabById(tabId) ?: return
        tabDao.updateTab(tab.copy(isActive = true, lastAccessed = System.currentTimeMillis()))
    }

    suspend fun updateTab(tabId: String, title: String? = null, url: String? = null) {
        val tab = tabDao.getTabById(tabId) ?: return
        tabDao.updateTab(
            tab.copy(
                title = title ?: tab.title,
                url = url ?: tab.url,
                lastAccessed = System.currentTimeMillis()
            )
        )
    }

    suspend fun closeTab(tabId: String) {
        tabDao.deleteTabById(tabId)
    }

    suspend fun getTabCount(workspaceId: String): Int {
        return tabDao.getTabCount(workspaceId)
    }

    suspend fun closeInactiveTabs(workspaceId: String, maxAge: Long) {
        val cutoff = System.currentTimeMillis() - maxAge
        tabDao.closeInactiveTabs(workspaceId, cutoff)
    }

    suspend fun clearIncognitoTabs() {
        tabDao.clearIncognitoTabs()
    }
}
