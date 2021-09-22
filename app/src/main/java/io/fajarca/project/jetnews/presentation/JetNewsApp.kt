package io.fajarca.project.jetnews.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme

@Composable
fun JetNewsApp(content: @Composable () -> Unit) {
    JetNewsTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}
