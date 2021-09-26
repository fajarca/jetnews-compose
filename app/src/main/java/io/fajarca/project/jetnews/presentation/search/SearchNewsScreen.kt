package io.fajarca.project.jetnews.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.list.NewsList

@Composable
fun SearchNewsScreen(viewModel: SearchNewsViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(text = "Search News") })

        val context = LocalContext.current
        NewsList(
            topHeadlines = viewModel.searchNews("iphone 13", "en").collectAsLazyPagingItems(),
            modifier = Modifier.weight(1f),
            onToggleBookmark = {},
            onHeadlineSelect = { headline -> NewsDetailActivity.start(context, headline.url) }
        )
    }
}
