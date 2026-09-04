package com.solara.browser.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.optima.OptimaEngine
import org.optima.OptimaView

@Composable
fun OptimaWebView(
    modifier: Modifier = Modifier,
    initialUrl: String = "https://example.com",
    onEngineReady: (OptimaEngine) -> Unit = {},
    onLoadStarted: (String?) -> Unit = {},
    onLoadFinished: (String?) -> Unit = {},
    onTitleChanged: (String?) -> Unit = {}
) {
    val context = LocalContext.current

    val engine = remember { OptimaEngine.create() }

    AndroidView(
        factory = { ctx: Context ->
            OptimaView(ctx).apply {
                setEngine(engine)
                onEngineReady(engine)
                loadUrl(initialUrl)
            }
        },
        modifier = modifier,
        update = { view -> }
    )

    DisposableEffect(Unit) {
        onDispose {
            engine.destroy()
        }
    }
}
