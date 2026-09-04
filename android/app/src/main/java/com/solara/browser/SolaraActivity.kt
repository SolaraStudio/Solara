package com.solara.browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.solara.browser.ui.BrowserScreen
import com.solara.browser.ui.theme.SolaraTheme
import org.optima.OptimaEngine

class SolaraActivity : ComponentActivity() {
    private var engine: OptimaEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolaraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BrowserScreen(
                        initialUrl = "https://example.com",
                        onEngineReady = { loadedEngine ->
                            engine = loadedEngine
                        }
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        engine?.pause()
    }

    override fun onResume() {
        super.onResume()
        engine?.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        engine?.destroy()
    }
}
