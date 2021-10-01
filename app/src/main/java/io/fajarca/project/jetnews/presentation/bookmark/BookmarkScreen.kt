package io.fajarca.project.jetnews.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
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
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.list.ArticleItem
import io.fajarca.project.jetnews.presentation.list.ArticleUiModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment

@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel, onNavigationIconClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = { Text(text = "Your bookmarks") },
            elevation = 6.dp,
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(Icons.Outlined.ArrowBack, null)
                }
            }
        )

        if (uiState.articles.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "No bookmarked articles yet.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        val context = LocalContext.current
        BookmarkedArticleList(
            articles = uiState.articles,
            modifier = Modifier.weight(1f),
            onToggleBookmark = { title -> viewModel.toggleBookmark(title) },
            onArticleSelect = { headline -> NewsDetailActivity.start(context, headline.url) }
        )
    }
}


@Composable
fun BookmarkedArticleList(
    articles: List<ArticleUiModel>,
    modifier: Modifier = Modifier,
    onToggleBookmark: (String) -> Unit,
    onArticleSelect: (ArticleUiModel) -> Unit
) {

    LazyColumn(modifier = modifier) {
        items(articles) {
            ArticleItem(
                article = it,
                onToggleBookmark = onToggleBookmark,
                onSelectArticle = onArticleSelect
            )
            Divider()
        }
    }
}
