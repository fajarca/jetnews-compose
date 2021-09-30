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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.search.SearchNewsActivity
import io.fajarca.project.jetnews.ui.components.CenteredCircularProgressIndicator
import io.fajarca.project.jetnews.ui.components.RemoteImage
import io.fajarca.project.jetnews.util.date.TimeDifference
import io.fajarca.project.jetnews.util.preview.ArticleProvider

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        AppBar(onSearchClicked = { SearchNewsActivity.start(context) })
        NewsList(
            articles = uiState.articles.collectAsLazyPagingItems(),
            modifier = Modifier.weight(1f),
            onToggleBookmark = { title -> viewModel.toggleBookmark(title) },
            onArticleSelect = { headline -> NewsDetailActivity.start(context, headline.url) }
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
    articles: LazyPagingItems<ArticleUiModel>,
    modifier: Modifier = Modifier,
    onToggleBookmark: (String) -> Unit,
    onArticleSelect: (ArticleUiModel) -> Unit
) {
    LazyColumn(modifier = modifier) {

        itemsIndexed(lazyPagingItems = articles) { index, items ->
            if (index == 0) {
                BannerNewsItem(
                    article = items ?: return@itemsIndexed,
                    onArticleSelect = onArticleSelect
                )
                Divider()
                return@itemsIndexed
            }

            NewsItem(
                headline = items ?: return@itemsIndexed,
                onToggleBookmark = onToggleBookmark,
                onSelectArticle = onArticleSelect
            )
            Divider()
        }

        articles.apply {
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
    headline: ArticleUiModel,
    onToggleBookmark: (String) -> Unit,
    onSelectArticle: (ArticleUiModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectArticle(headline) }
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

        NewsContent(article = headline, Modifier.weight(1f))

        BookmarkButton(
            isBookmarked = headline.isBookmarked,
            onClick = { onToggleBookmark(headline.title) })

    }
}

@Composable
fun NewsContent(article: ArticleUiModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = article.title,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = article.source,
                style = MaterialTheme.typography.body2
            )
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = when(article.timeDifference) {
                    is TimeDifference.Day -> "${article.timeDifference.days} hari yang lalu"
                    is TimeDifference.Hours -> "${article.timeDifference.hours} jam yang lalu"
                    is TimeDifference.Minute -> "${article.timeDifference.minutes} menit yang lalu"
                    TimeDifference.Unknown -> "-"
                },
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
fun BannerNewsItem(article: ArticleUiModel, onArticleSelect: (ArticleUiModel) -> Unit) {
    Column(modifier = Modifier.clickable { onArticleSelect(article) }) {
        RemoteImage(
            url = article.imageUrl,
            modifier = Modifier.height(220.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = article.title,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(8.dp)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = article.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }

}

@Preview("ArticleUiModel news (bookmarked)")
@Composable
fun MainScreenPreview() {
    MainScreen(hiltViewModel())
}


@Preview("ArticleUiModel news (bookmarked)")
@Composable
fun NewsItemPreview(@PreviewParameter(ArticleProvider::class) article : ArticleUiModel) {
    NewsItem(article, {}, {})
}

@Preview("ArticleUiModel news")
@Composable
fun NewsItemBookmarkedPreview(@PreviewParameter(ArticleProvider::class) article : ArticleUiModel) {
    NewsItem(article, {}, {})
}


@Preview("ArticleUiModel news (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsItemBookmarkedDarkPreview(@PreviewParameter(ArticleProvider::class) article : ArticleUiModel) {
    NewsItem(article, {}, {})
}


@Preview("Banner Item Card")
@Composable
fun BannerItemCardPreview(@PreviewParameter(ArticleProvider::class) article : ArticleUiModel) {
    BannerNewsItem(article, {})
}