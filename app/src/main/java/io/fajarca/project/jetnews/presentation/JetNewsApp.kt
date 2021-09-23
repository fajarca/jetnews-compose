package io.fajarca.project.jetnews.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import io.fajarca.project.jetnews.ui.components.AppBar
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme

@Composable
fun JetNewsApp(content: @Composable () -> Unit) {
    JetNewsTheme {
        Scaffold(
            topBar = { AppBar() }
        ) {
            content()
        }
    }
}

