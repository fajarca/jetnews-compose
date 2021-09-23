package io.fajarca.project.jetnews.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun AppBar(title: String = "JetNews") {
    TopAppBar(
        title = { Text(text = title) }
    )
}
