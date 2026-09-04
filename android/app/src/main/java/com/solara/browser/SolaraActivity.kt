package com.solara.browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.solara.browser.navigation.SolaraNavGraph
import com.solara.browser.ui.theme.SolaraTheme
import com.solara.browser.ui.viewmodel.BrowserViewModel
import com.solara.browser.ui.viewmodel.SettingsViewModel
import org.optima.OptimaEngine

class SolaraActivity : ComponentActivity() {
    private var engine: OptimaEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val intentUrl = intent?.data?.toString()

        setContent {
            SolaraTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val browserViewModel: BrowserViewModel = viewModel()
                    val settingsViewModel: SettingsViewModel = viewModel()

                    SolaraNavGraph(
                        navController = navController,
                        browserViewModel = browserViewModel,
                        settingsViewModel = settingsViewModel,
                        initialUrl = intentUrl ?: "https://example.com"
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
