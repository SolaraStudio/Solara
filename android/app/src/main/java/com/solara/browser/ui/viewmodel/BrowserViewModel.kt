package com.solara.browser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.solara.browser.SolaraApp
import com.solara.browser.data.model.BookmarkEntity
import com.solara.browser.data.model.HistoryEntity
import com.solara.browser.data.model.TabInfo
import com.solara.browser.data.model.WorkspaceEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BrowserViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as SolaraApp
    private val tabRepo = app.tabRepository
    private val workspaceRepo = app.workspaceRepository
    private val bookmarkRepo = app.bookmarkRepository
    private val historyRepo = app.historyRepository
    private val settingsRepo = app.settingsRepository

    private val _currentUrl = MutableStateFlow("https://example.com")
    val currentUrl: StateFlow<String> = _currentUrl.asStateFlow()

    private val _currentTitle = MutableStateFlow("New Tab")
    val currentTitle: StateFlow<String> = _currentTitle.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _canGoBack = MutableStateFlow(false)
    val canGoBack: StateFlow<Boolean> = _canGoBack.asStateFlow()

    private val _canGoForward = MutableStateFlow(false)
    val canGoForward: StateFlow<Boolean> = _canGoForward.asStateFlow()

    private val _activeTabId = MutableStateFlow<String?>(null)
    val activeTabId: StateFlow<String?> = _activeTabId.asStateFlow()

    private val _activeWorkspaceId = MutableStateFlow("default")
    val activeWorkspaceId: StateFlow<String> = _activeWorkspaceId.asStateFlow()

    private val _isTabsOpen = MutableStateFlow(false)
    val isTabsOpen: StateFlow<Boolean> = _isTabsOpen.asStateFlow()

    private val _isSplitView = MutableStateFlow(false)
    val isSplitView: StateFlow<Boolean> = _isSplitView.asStateFlow()

    private val _splitViewUrl = MutableStateFlow("https://example.com")
    val splitViewUrl: StateFlow<String> = _splitViewUrl.asStateFlow()

    private val _isIncognito = MutableStateFlow(false)
    val isIncognito: StateFlow<Boolean> = _isIncognito.asStateFlow()

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    val tabs: StateFlow<List<TabInfo>> = tabRepo.getTabsByWorkspace("default")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workspaces: StateFlow<List<WorkspaceEntity>> = workspaceRepo.getAllWorkspaces()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<BookmarkEntity>> = bookmarkRepo.getAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val history: StateFlow<List<HistoryEntity>> = historyRepo.getRecentHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun navigateTo(url: String) {
        val normalized = normalizeUrl(url)
        _currentUrl.value = normalized
        viewModelScope.launch {
            tabRepo.updateTab(_activeTabId.value ?: return@launch, url = normalized)
            historyRepo.addEntry(_currentTitle.value, normalized)
            checkBookmarkState(normalized)
        }
    }

    fun goBack() {
        _canGoBack.value = false
    }

    fun goForward() {
        _canGoForward.value = false
    }

    fun refresh() {
        _isLoading.value = true
    }

    fun onLoadingStarted(url: String?) {
        _isLoading.value = true
        url?.let {
            _currentUrl.value = it
            viewModelScope.launch {
                tabRepo.updateTab(_activeTabId.value ?: return@launch, url = it)
            }
        }
    }

    fun onLoadingFinished(url: String?, title: String?) {
        _isLoading.value = false
        url?.let {
            _currentUrl.value = it
            title?.let { t -> _currentTitle.value = t }
            viewModelScope.launch {
                tabRepo.updateTab(
                    _activeTabId.value ?: return@launch,
                    url = it,
                    title = title
                )
                historyRepo.addEntry(title ?: it, it)
                checkBookmarkState(it)
            }
        }
    }

    fun toggleTabs() {
        _isTabsOpen.value = !_isTabsOpen.value
    }

    fun closeTabsPanel() {
        _isTabsOpen.value = false
    }

    fun createTab() {
        viewModelScope.launch {
            val tab = tabRepo.createTab(
                workspaceId = _activeWorkspaceId.value,
                url = "https://example.com",
                title = "New Tab"
            )
            _activeTabId.value = tab.id
            _currentUrl.value = tab.url
            _currentTitle.value = tab.title
        }
    }

    fun selectTab(tab: TabInfo) {
        viewModelScope.launch {
            tabRepo.selectTab(tab.id, _activeWorkspaceId.value)
            _activeTabId.value = tab.id
            _currentUrl.value = tab.url
            _currentTitle.value = tab.title
            _isTabsOpen.value = false
        }
    }

    fun closeTab(tabId: String) {
        viewModelScope.launch {
            tabRepo.closeTab(tabId)
            if (tabId == _activeTabId.value) {
                _activeTabId.value = null
            }
        }
    }

    fun createWorkspace(name: String) {
        viewModelScope.launch {
            workspaceRepo.createWorkspace(name)
        }
    }

    fun switchWorkspace(workspaceId: String) {
        _activeWorkspaceId.value = workspaceId
    }

    fun deleteWorkspace(id: String) {
        viewModelScope.launch {
            workspaceRepo.deleteWorkspace(id)
        }
    }

    fun toggleBookmark() {
        viewModelScope.launch {
            bookmarkRepo.toggleBookmark(_currentTitle.value, _currentUrl.value)
            checkBookmarkState(_currentUrl.value)
        }
    }

    fun removeBookmark(id: Long) {
        viewModelScope.launch {
            bookmarkRepo.removeBookmark(id)
            checkBookmarkState(_currentUrl.value)
        }
    }

    fun deleteHistoryEntry(id: Long) {
        viewModelScope.launch {
            historyRepo.removeEntry(id)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            historyRepo.clearAll()
        }
    }

    fun toggleSplitView() {
        _isSplitView.value = !_isSplitView.value
    }

    fun navigateSplitView(url: String) {
        _splitViewUrl.value = normalizeUrl(url)
    }

    fun startIncognito() {
        _isIncognito.value = true
    }

    fun stopIncognito() {
        _isIncognito.value = false
        viewModelScope.launch {
            tabRepo.clearIncognitoTabs()
        }
    }

    fun setNavigationState(canBack: Boolean, canForward: Boolean) {
        _canGoBack.value = canBack
        _canGoForward.value = canForward
    }

    private suspend fun checkBookmarkState(url: String) {
        _isBookmarked.value = bookmarkRepo.isBookmarked(url)
    }

    private fun normalizeUrl(input: String): String {
        val trimmed = input.trim()
        if (trimmed.isBlank()) return "about:blank"
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) return trimmed
        if (trimmed.contains(".") && !trimmed.contains(" ")) {
            return "https://$trimmed"
        }
        return "https://www.google.com/search?q=${java.net.URLEncoder.encode(trimmed, "UTF-8")}"
    }
}
