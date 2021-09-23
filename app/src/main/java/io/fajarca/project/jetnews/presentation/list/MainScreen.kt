package io.fajarca.project.jetnews.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.fajarca.project.jetnews.domain.entity.TopHeadline
import io.fajarca.project.jetnews.ui.components.FullscreenLoading


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxHeight()) {
        AppBar(onSearchClicked = {
            viewModel.getNewsSource()
        })

        if (uiState.isLoading) {
            FullscreenLoading()
        }

        NewsList(
            topHeadlines = uiState.topHeadlines,
            modifier = Modifier.weight(1f),
            onToggleBookmark = { title -> viewModel.toggleFavorite(title) },
            onHeadlineSelect = { headline -> }
        )
    }
}

@Composable
fun AppBar(onSearchClicked: () -> Unit) {
    TopAppBar(title = { Text(text = "JetNews") }, actions = {
        IconButton(onClick = onSearchClicked) {
            Icon(Icons.Default.Search, null)
        }
    })
}

@Composable
fun NewsList(
    topHeadlines: List<TopHeadline>,
    modifier: Modifier = Modifier,
    onToggleBookmark: (String) -> Unit,
    onHeadlineSelect: (TopHeadline) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = modifier, state = listState) {
        items(items = topHeadlines) {
            NewsItem(
                headline = it,
                onToggleBookmark = onToggleBookmark,
                onHeadlineSelect = onHeadlineSelect
            )
        }
    }
}

@Composable
fun NewsItem(
    headline: TopHeadline,
    onToggleBookmark: (String) -> Unit,
    onHeadlineSelect: (TopHeadline) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onHeadlineSelect(headline) }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            NewsImage(
                headline.imageUrl,
                Modifier
                    .size(120.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = headline.title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1
                )

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = headline.source,
                        style = MaterialTheme.typography.body2
                    )
                }

            }

            BookmarkButton(
                isBookmarked = headline.isBookmarked,
                onClick = { onToggleBookmark(headline.title) })

        }

    }


}

@ExperimentalCoilApi
@Composable
fun NewsImage(url: String, modifier: Modifier) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = null,
        modifier = modifier,
        alignment = Alignment.Center,
        contentScale = ContentScale.FillHeight
    )
}

@Composable
fun BookmarkButton(isBookmarked: Boolean, onClick: () -> Unit) {
    IconToggleButton(checked = isBookmarked, onCheckedChange = { onClick() }) {
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = if (isBookmarked) "Bookmarked" else "Unbookmark"
        )
    }
}

@Preview("Headline news (bookmarked)")
@Composable
fun NewsItemPreview() {
    NewsItem(
        TopHeadline(
            "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa",
            "2021-09-23T05:55:54Z",
            "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa - Kontan",
            "https://lifestyle.kontan.co.id/news/duh-bug-ios-15-menganggap-ruang-penyimpanan-penuh-meskipun-masih-ada-sisa",
            "https://foto.kontan.co.id/H3LwljVMcQdeUbi8U_XTzM-v8T0=/smart/2020/10/14/963412751p.jpg",
            "Kontan.co.id",
            true,
        ),
        {},
        {}
    )
}

@Preview("Headline news")
@Composable
fun NewsItemBookmarkedPreview() {
    NewsItem(
        TopHeadline(
            "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa",
            "2021-09-23T05:55:54Z",
            "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa - Kontan",
            "https://lifestyle.kontan.co.id/news/duh-bug-ios-15-menganggap-ruang-penyimpanan-penuh-meskipun-masih-ada-sisa",
            "https://foto.kontan.co.id/H3LwljVMcQdeUbi8U_XTzM-v8T0=/smart/2020/10/14/963412751p.jpg",
            "Kontan.co.id",
            false,
        ),
        {},
        {}
    )
}

@Preview("Toolbar")
@Composable
fun ToolbarPreview() {
    AppBar({})
}