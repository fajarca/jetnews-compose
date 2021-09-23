package io.fajarca.project.jetnews.presentation.detail

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun NewsDetailScreen(url: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "News Detail") },
            elevation = 6.dp,
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.ArrowBack, null)
                }
            }
        )
        AndroidView(
            factory = { context ->
                WebView(context).apply {
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
}