package com.solara.browser.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.weight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.solara.browser.ui.components.OptimaWebView
import org.optima.OptimaEngine

@Composable
fun BrowserScreen(
    initialUrl: String = "https://example.com",
    onEngineReady: (OptimaEngine) -> Unit = {}
) {
    var engine by remember { mutableStateOf<OptimaEngine?>(null) }
    var currentUrl by remember { mutableStateOf(initialUrl) }
    var isLoading by remember { mutableStateOf(false) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = if (isLoading) "Loading..." else currentUrl) },
            navigationIcon = {
                IconButton(
                    onClick = { engine?.goBack() },
                    enabled = canGoBack
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(
                    onClick = { engine?.goForward() },
                    enabled = canGoForward
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Forward")
                }
                IconButton(onClick = { engine?.reload() }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }
        )

        OptimaWebView(
            modifier = Modifier.weight(1f),
            onEngineReady = { loadedEngine ->
                engine = loadedEngine
                onEngineReady(loadedEngine)
                loadedEngine.loadUrl(currentUrl)
            },
            onLoadStarted = { url ->
                isLoading = true
                url?.let { currentUrl = it }
            },
            onLoadFinished = { url ->
                isLoading = false
                url?.let { currentUrl = it }
                // Update navigation state from engine if available
                engine?.let {
                    // canGoBack = it.canGoBack()
                    // canGoForward = it.canGoForward()
                }
            }
        )
    }
}
