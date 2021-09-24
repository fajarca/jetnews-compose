package io.fajarca.project.jetnews.ui.components

import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(url : String, modifier : Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            android.webkit.WebView(context).apply {
                loadUrl(url)
                webViewClient = WebViewClient()
            }
        },
        modifier = modifier,
        update = {
            it.loadUrl(url)
        }
    )
}