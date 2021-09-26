package io.fajarca.project.jetnews.presentation.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import io.fajarca.project.jetnews.domain.entity.Article
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.search.SearchNewsActivity
import io.fajarca.project.jetnews.ui.components.CenteredCircularProgressIndicator
import io.fajarca.project.jetnews.ui.components.RemoteImage

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        AppBar(onSearchClicked = { SearchNewsActivity.start(context) })
        NewsList(
            topHeadlines = uiState.headlines.collectAsLazyPagingItems(),
            modifier = Modifier.weight(1f),
            onToggleBookmark = { title -> viewModel.toggleBookmark(title) },
            onHeadlineSelect = { headline -> NewsDetailActivity.start(context, headline.url) }
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
    topHeadlines: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
    onToggleBookmark: (String) -> Unit,
    onHeadlineSelect: (Article) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = modifier, state = listState) {

        itemsIndexed(lazyPagingItems = topHeadlines) { index, items ->
            if (index == 0) {
                BannerNewsItem(
                    headline = items ?: return@itemsIndexed,
                    onHeadlineSelect = onHeadlineSelect
                )
                Divider()
                return@itemsIndexed
            }

            NewsItem(
                headline = items ?: return@itemsIndexed,
                onToggleBookmark = onToggleBookmark,
                onHeadlineSelect = onHeadlineSelect
            )
            Divider()
        }

        topHeadlines.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CenteredCircularProgressIndicator() }
                }
                loadState.append is LoadState.Loading -> {
                    item { CenteredCircularProgressIndicator() }
                }

            }
        }
    }
}

@Composable
fun NewsItem(
    headline: Article,
    onToggleBookmark: (String) -> Unit,
    onHeadlineSelect: (Article) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onHeadlineSelect(headline) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        RemoteImage(
            url = headline.imageUrl,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillHeight
        )

        NewsContent(headline = headline, Modifier.weight(1f))

        BookmarkButton(
            isBookmarked = headline.isBookmarked,
            onClick = { onToggleBookmark(headline.title) })

    }
}

@Composable
fun NewsContent(headline: Article, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
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

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = headline.publishedAt,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}

@Composable
fun BookmarkButton(isBookmarked: Boolean, onClick: () -> Unit) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        IconToggleButton(checked = isBookmarked, onCheckedChange = { onClick() }) {
            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = if (isBookmarked) "Bookmarked" else "Unbookmark",
            )
        }
    }
}

@Composable
fun BannerNewsItem(headline: Article, onHeadlineSelect: (Article) -> Unit) {
    Column(modifier = Modifier.clickable { onHeadlineSelect(headline) }) {
        RemoteImage(
            url = headline.imageUrl,
            modifier = Modifier.height(220.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = headline.title,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(8.dp)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = headline.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }

}


@Preview("Headline news (bookmarked)")
@Composable
fun NewsItemPreview() {
    NewsItem(
        Article(
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
        Article(
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


@Preview("Headline news (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsItemBookmarkedDarkPreview() {
    NewsItem(
        Article(
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


@Preview("Banner Item Card")
@Composable
fun BannerItemCardPreview() {
    BannerNewsItem(
        headline = Article(
            "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa",
            "2021-09-23T05:55:54Z",
            "Duh! Bug iOS 15 menganggap ruang penyimpanan penuh meskipun masih ada sisa - Kontan",
            "https://lifestyle.kontan.co.id/news/duh-bug-ios-15-menganggap-ruang-penyimpanan-penuh-meskipun-masih-ada-sisa",
            "https://foto.kontan.co.id/H3LwljVMcQdeUbi8U_XTzM-v8T0=/smart/2020/10/14/963412751p.jpg",
            "Kontan.co.id",
            false,
        ),
        {}
    )
}