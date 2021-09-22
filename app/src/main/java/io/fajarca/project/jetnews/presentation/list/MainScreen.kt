package io.fajarca.project.jetnews.presentation.list

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.fajarca.project.jetnews.domain.entity.TopHeadline


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxHeight()) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        NewsList(
            topHeadlines = uiState.topHeadlines,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NewsList(topHeadlines: List<TopHeadline>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = topHeadlines)  {
            NewsItem(title = it.title)
            Divider()
        }
    }
}

@Composable
fun NewsItem(title: String) {
    var isSelected by remember { mutableStateOf(false) }

    val targetColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else Color.Transparent
    )

    Surface(color = targetColor) {
        Text(
            text = title,
            modifier = Modifier
                .clickable { isSelected = !isSelected }
                .fillMaxWidth(1f)
                .padding(16.dp)
        )
    }

}
