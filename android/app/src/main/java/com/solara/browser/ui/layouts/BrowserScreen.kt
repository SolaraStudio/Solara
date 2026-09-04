package com.solara.browser.ui.layouts

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solara.browser.ui.components.BottomToolbar
import com.solara.browser.ui.components.GlassUrlBar
import com.solara.browser.ui.components.HomeScreen
import com.solara.browser.ui.components.OptimaWebView
import com.solara.browser.ui.components.SearchSuggestion
import com.solara.browser.ui.components.SearchSuggestions
import com.solara.browser.ui.components.Shortcut
import com.solara.browser.ui.components.Tab
import com.solara.browser.ui.components.VerticalTabs
import com.solara.browser.ui.components.Workspace
import com.solara.browser.ui.theme.SolaraColors
import com.solara.browser.ui.viewmodel.BrowserViewModel

@Composable
fun BrowserScreen(
    initialUrl: String = "https://example.com",
    viewModel: BrowserViewModel = viewModel()
) {
    val context = LocalContext.current

    val currentUrl by viewModel.currentUrl.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()
    val canGoForward by viewModel.canGoForward.collectAsState()
    val isTabsOpen by viewModel.isTabsOpen.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    val tabs by viewModel.tabs.collectAsState()
    val workspaces by viewModel.workspaces.collectAsState()
    val activeTabId by viewModel.activeTabId.collectAsState()
    val activeWorkspaceId by viewModel.activeWorkspaceId.collectAsState()
    val isSplitView by viewModel.isSplitView.collectAsState()

    var showHome by remember { mutableStateOf(true) }
    var isSearchFocused by remember { mutableStateOf(false) }

    val shortcuts = listOf(
        Shortcut("Google", "https://google.com", Icons.Default.Language, SolaraColors.Accent),
        Shortcut("YouTube", "https://youtube.com", Icons.Default.PlayCircle, Color(0xFFFF0000)),
        Shortcut("Reddit", "https://reddit.com", Icons.Default.Forum, Color(0xFFFF4500)),
        Shortcut("GitHub", "https://github.com", Icons.Default.Code, SolaraColors.TextPrimary),
        Shortcut("News", "https://news.ycombinator.com", Icons.Default.Newspaper, Color(0xFFFF6600)),
        Shortcut("Shopping", "https://amazon.com", Icons.Default.ShoppingBag, Color(0xFFFF9900)),
        Shortcut("Sports", "https://espn.com", Icons.Default.SportsEsports, Color(0xFFE23636)),
        Shortcut("Browse", "https://wikipedia.org", Icons.Default.Language, SolaraColors.TextTertiary)
    )

    val drawerTabs = tabs.map { tab ->
        Tab(id = tab.id, title = tab.title, url = tab.url, isActive = tab.id == activeTabId)
    }

    val drawerWorkspaces = workspaces.map { ws ->
        Workspace(id = ws.id, name = ws.name, color = ws.color)
    }

    LaunchedEffect(initialUrl) {
        if (initialUrl.isNotBlank() && initialUrl != "about:blank" && initialUrl != "https://example.com") {
            showHome = false
            viewModel.navigateTo(initialUrl)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = SolaraColors.SolaraColors.BackgroundGradient)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 50f) {
                        viewModel.toggleTabs()
                    }
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            GlassUrlBar(
                url = currentUrl,
                isLoading = isLoading,
                onRefresh = { viewModel.refresh() },
                onToggleTabs = { viewModel.toggleTabs() },
                onNavigate = { url ->
                    showHome = false
                    viewModel.navigateTo(url)
                    isSearchFocused = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Box(modifier = Modifier.weight(1f)) {
                if (showHome) {
                    HomeScreen(
                        shortcuts = shortcuts,
                        onShortcutClick = { url ->
                            showHome = false
                            viewModel.navigateTo(url)
                        }
                    )
                } else {
                    if (isSplitView) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            OptimaWebView(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                initialUrl = currentUrl,
                                onEngineReady = { viewModel.setNavigationState(false, false) },
                                onLoadStarted = { viewModel.onLoadingStarted(it) },
                                onLoadFinished = { url, title -> viewModel.onLoadingFinished(url, title) }
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(SolaraColors.Accent.copy(alpha = 0.3f))
                            )
                            OptimaWebView(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                initialUrl = viewModel.splitViewUrl.value,
                                onEngineReady = {},
                                onLoadStarted = {},
                                onLoadFinished = { _, _ -> }
                            )
                        }
                    } else {
                        OptimaWebView(
                            modifier = Modifier.fillMaxSize(),
                            initialUrl = currentUrl,
                            onEngineReady = { viewModel.setNavigationState(false, false) },
                            onLoadStarted = { viewModel.onLoadingStarted(it) },
                            onLoadFinished = { url, title -> viewModel.onLoadingFinished(url, title) }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isSearchFocused,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    SearchSuggestions(
                        suggestions = emptyList(),
                        onSuggestionClick = { url ->
                            showHome = false
                            viewModel.navigateTo(url)
                            isSearchFocused = false
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            if (!isSearchFocused) {
                BottomToolbar(
                    canGoBack = canGoBack,
                    canGoForward = canGoForward,
                    isBookmarked = isBookmarked,
                    onBack = { viewModel.goBack() },
                    onForward = { viewModel.goForward() },
                    onHome = {
                        showHome = true
                        viewModel.navigateTo("about:blank")
                    },
                    onBookmark = { viewModel.toggleBookmark() },
                    onShare = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, currentUrl)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share"))
                    },
                    onSearch = { isSearchFocused = true },
                    onMore = { }
                )
            }
        }

        VerticalTabs(
            tabs = drawerTabs,
            workspaces = drawerWorkspaces,
            currentTabId = activeTabId,
            activeWorkspaceId = activeWorkspaceId,
            isVisible = isTabsOpen,
            onTabSelected = { tab ->
                viewModel.selectTab(
                    com.solara.browser.data.model.TabInfo(id = tab.id, title = tab.title, url = tab.url)
                )
                showHome = false
            },
            onTabClosed = { viewModel.closeTab(it) },
            onNewTab = { viewModel.createTab() },
            onClose = { viewModel.closeTabsPanel() },
            onWorkspaceSelected = { viewModel.switchWorkspace(it) },
            onNewWorkspace = { viewModel.createWorkspace("Workspace") },
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 64.dp)
        )
    }
}

private fun Modifier.height(dp: androidx.compose.ui.unit.Dp): Modifier {
    return this.then(Modifier.padding(0.dp))
}
