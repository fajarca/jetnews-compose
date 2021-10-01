package io.fajarca.project.jetnews.presentation.search_result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.list.ArticleList

@Composable
fun SearchResultScreen(query : String, viewModel: SearchResultViewModel, onNavigationIconClick : () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = { Text(text = query) },
            elevation = 6.dp,
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(Icons.Outlined.ArrowBack, null)
                }
            }
        )

        val context = LocalContext.current
        ArticleList(
            articles = uiState.searchResult.collectAsLazyPagingItems(),
            modifier = Modifier.weight(1f),
            onToggleBookmark = { title -> viewModel.toggleBookmark(title) },
            onArticleSelect = { headline -> NewsDetailActivity.start(context, headline.url) }
        )
    }
}
