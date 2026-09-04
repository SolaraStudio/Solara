package com.solara.browser.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.optima.OptimaEngine
import org.optima.OptimaView

@Composable
fun OptimaWebView(
    modifier: Modifier = Modifier,
    onEngineReady: (OptimaEngine) -> Unit = {},
    onLoadStarted: (String?) -> Unit = {},
    onLoadFinished: (String?) -> Unit = {}
) {
    AndroidView(
        factory = { context: Context ->
            OptimaView(context).apply {
                val engine = OptimaEngine.create()
                setEngine(engine)
                onEngineReady(engine)
            }
        },
        modifier = modifier,
        update = { }
    )
}
