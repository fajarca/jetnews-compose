package io.fajarca.project.jetnews.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme

@Composable
fun JetNewsApp(content: @Composable () -> Unit) {
    JetNewsTheme {
        Scaffold(topBar = { AppBar() }) {
            content()
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(title = { Text(text = "JetNews") })
}
