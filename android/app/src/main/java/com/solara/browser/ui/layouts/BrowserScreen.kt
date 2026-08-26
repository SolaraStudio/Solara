package com.solara.browser.ui.layouts

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.solara.browser.ui.components.GlassUrlBar
import com.solara.browser.ui.components.Tab
import com.solara.browser.ui.components.VerticalTabs
import java.util.UUID

@Composable
fun BrowserScreen() {
    var currentUrl by remember { mutableStateOf("https://github.com/frostre1997") }
    var isLoading by remember { mutableStateOf(false) }
    var showTabs by remember { mutableStateOf(false) }
    var webView by remember { mutableStateOf<WebView?>(null) }

    var tabs by remember {
        mutableStateOf(
            listOf(
                Tab(
                    id = UUID.randomUUID().toString(),
                    title = "GitHub",
                    url = "https://github.com/frostre1997",
                    isActive = true
                )
            )
        )
    }
    var currentTabId by remember { mutableStateOf(tabs.firstOrNull()?.id) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        allowFileAccess = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        setSupportZoom(true)
                        builtInZoomControls = true
                        displayZoomControls = false
                        cacheMode = WebSettings.LOAD_DEFAULT
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                            url?.let {
                                currentUrl = it
                                tabs = tabs.map { tab ->
                                    if (tab.id == currentTabId) tab.copy(url = it)
                                    else tab
                                }
                            }
                            view?.title?.let { title ->
                                tabs = tabs.map { tab ->
                                    if (tab.id == currentTabId) tab.copy(title = title)
                                    else tab
                                }
                            }
                        }

                        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                            url?.let {
                                currentUrl = it
                                tabs = tabs.map { tab ->
                                    if (tab.id == currentTabId) tab.copy(url = it)
                                    else tab
                                }
                                view?.loadUrl(it)
                            }
                            return true
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            isLoading = newProgress < 100
                        }

                        override fun onReceivedTitle(view: WebView?, title: String?) {
                            title?.let {
                                tabs = tabs.map { tab ->
                                    if (tab.id == currentTabId) tab.copy(title = it)
                                    else tab
                                }
                            }
                        }
                    }

                    loadUrl(currentUrl)
                    webView = this
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        GlassUrlBar(
            url = currentUrl,
            isLoading = isLoading,
            onRefresh = { webView?.reload() },
            onToggleTabs = { showTabs = !showTabs },
            onNavigate = { newUrl ->
                val finalUrl = if (newUrl.startsWith("http://") || newUrl.startsWith("https://")) {
                    newUrl
                } else {
                    "https://$newUrl"
                }
                currentUrl = finalUrl
                tabs = tabs.map { tab ->
                    if (tab.id == currentTabId) tab.copy(url = finalUrl)
                    else tab
                }
                webView?.loadUrl(finalUrl)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        )

        if (showTabs) {
            VerticalTabs(
                tabs = tabs,
                currentTabId = currentTabId,
                onTabSelected = { tab ->
                    currentTabId = tab.id
                    currentUrl = tab.url
                    tabs = tabs.map { it.copy(isActive = it.id == tab.id) }
                    webView?.loadUrl(tab.url)
                    showTabs = false
                },
                onTabClosed = { tabId ->
                    tabs = tabs.filter { it.id != tabId }
                    if (tabs.isEmpty()) {
                        val newTab = Tab(
                            id = UUID.randomUUID().toString(),
                            title = "New Tab",
                            url = "https://google.com",
                            isActive = true
                        )
                        tabs = listOf(newTab)
                        currentTabId = newTab.id
                        currentUrl = newTab.url
                        webView?.loadUrl(newTab.url)
                    } else {
                        val activeTab = tabs.firstOrNull { it.isActive } ?: tabs.first()
                        currentTabId = activeTab.id
                        currentUrl = activeTab.url
                        webView?.loadUrl(activeTab.url)
                    }
                },
                onNewTab = {
                    val newTab = Tab(
                        id = UUID.randomUUID().toString(),
                        title = "New Tab",
                        url = "https://google.com",
                        isActive = true
                    )
                    tabs = tabs.map { it.copy(isActive = false) } + newTab
                    currentTabId = newTab.id
                    currentUrl = newTab.url
                    webView?.loadUrl(newTab.url)
                    showTabs = false
                },
                onClose = { showTabs = false },
                modifier = Modifier
                    .fillMaxSize()
                    .width(280.dp)
            )
        }
    }
}
