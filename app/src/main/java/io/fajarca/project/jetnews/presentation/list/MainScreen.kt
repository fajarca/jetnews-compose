package io.fajarca.project.jetnews.presentation.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.fajarca.project.jetnews.presentation.bookmark.BookmarkActivity
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.search.SearchNewsActivity
import io.fajarca.project.jetnews.ui.components.AppBar
import io.fajarca.project.jetnews.ui.components.CenteredCircularProgressIndicator
import io.fajarca.project.jetnews.ui.components.RemoteImage
import io.fajarca.project.jetnews.util.date.TimeDifference
import io.fajarca.project.jetnews.util.preview.ArticleUiModelProvider
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            TopAppBar(
                bookmarkCount = state.bookmarkCount,
                onSearchClick = { viewModel.setEvent(MainContract.Event.SearchArticle) },
                onViewSavedBookmarkClick = { viewModel.setEvent(MainContract.Event.ViewBookmarkedArticle) }
            )
        },
        scaffoldState = scaffoldState
    ) {
        MainScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = { event -> viewModel.setEvent(event) },
            onNavigationRequested = { navigation ->
                when (navigation) {
                    is MainContract.Effect.Navigation.ToArticleDetail -> NewsDetailActivity.start(
                        context,
                        navigation.article.url
                    )
                    MainContract.Effect.Navigation.ToSearchArticleScreen -> SearchNewsActivity.start(
                        context
                    )
                    MainContract.Effect.Navigation.ToBookmarkScreen -> BookmarkActivity.start(context)
                }
            },
            scaffoldState = scaffoldState
        )
    }


}

@Composable
fun MainScreen(
    state: MainContract.State,
    effectFlow: Flow<MainContract.Effect>,
    onEventSent: (event: MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit,
    scaffoldState: ScaffoldState
) {
    val articles = state.articles.collectAsLazyPagingItems()

    //A LaunchedEffect guarantees that the coroutine is launched only once and not on every recomposition
    LaunchedEffect(Unit) {
        effectFlow
            .onEach { effect ->
                when (effect) {
                    is MainContract.Effect.Navigation.ToArticleDetail -> onNavigationRequested(
                        effect
                    )
                    MainContract.Effect.Navigation.ToSearchArticleScreen -> onNavigationRequested(
                        MainContract.Effect.Navigation.ToSearchArticleScreen
                    )
                    MainContract.Effect.Navigation.ToBookmarkScreen -> onNavigationRequested(
                        MainContract.Effect.Navigation.ToBookmarkScreen
                    )
                    MainContract.Effect.ShowRefreshSuccessSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar("Successfully refreshed")
                    }
                    MainContract.Effect.PullRefresh -> articles.refresh()
                }
            }
            .collect()
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.loading),
        onRefresh = { onEventSent(MainContract.Event.PullRefresh) }
    ) {
        ArticleList(
            articles = articles,
            modifier = Modifier.fillMaxSize(),
            onToggleBookmark = { article ->
                onEventSent(MainContract.Event.BookmarkArticle(article))
            },
            onArticleSelect = { article ->
                onEventSent(MainContract.Event.ArticleSelection(article))
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopAppBar(bookmarkCount: Int, onSearchClick: () -> Unit, onViewSavedBookmarkClick: () -> Unit) {
    AppBar(
        title = "JetNews",
        actions = {
            BookmarkBadgeCount(
                bookmarkCount = bookmarkCount,
                onViewSavedBookmarkClick = onViewSavedBookmarkClick
            )

            IconButton(onClick = onSearchClick) {
                Icon(
                    modifier = Modifier.padding(top = 2.dp),
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}


@Composable
fun ArticleList(
    articles: LazyPagingItems<ArticleUiModel>,
    modifier: Modifier = Modifier,
    onToggleBookmark: (ArticleUiModel) -> Unit,
    onArticleSelect: (ArticleUiModel) -> Unit
) {
    LazyColumn(modifier = modifier) {

        itemsIndexed(lazyPagingItems = articles) { index, items ->
            if (index == 0) {
                BannerArticleItem(
                    article = items ?: return@itemsIndexed,
                    onArticleSelect = onArticleSelect
                )
                Divider()
                return@itemsIndexed
            }

            ArticleItem(
                article = items ?: return@itemsIndexed,
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
fun ArticleItem(
    article: ArticleUiModel,
    onToggleBookmark: (ArticleUiModel) -> Unit,
    onSelectArticle: (ArticleUiModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectArticle(article) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        RemoteImage(
            url = article.imageUrl,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        ArticleContent(article = article, Modifier.weight(1f))

        BookmarkButton(
            isBookmarked = article.isBookmarked,
            onClick = { onToggleBookmark(article) }
        )

    }
}

@Composable
fun ArticleContent(article: ArticleUiModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = article.title,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onPrimary)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = article.source,
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onPrimary)
            )
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = when (article.timeDifference) {
                    is TimeDifference.Day -> "${article.timeDifference.days} hari yang lalu"
                    is TimeDifference.Hours -> "${article.timeDifference.hours} jam yang lalu"
                    is TimeDifference.Minute -> "${article.timeDifference.minutes} menit yang lalu"
                    TimeDifference.Unknown -> "-"
                },
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onPrimary),
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
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun BookmarkBadgeCount(bookmarkCount: Int, onViewSavedBookmarkClick: () -> Unit) {
    IconButton(onClick = onViewSavedBookmarkClick) {
        Box {
            Icon(
                modifier = Modifier.padding(top = 2.dp),
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary
            )
            if (bookmarkCount > 0) {
                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Red)
                        .padding(start = 4.dp, end = 4.dp, top = 3.dp, bottom = 3.dp)
                        .align(Alignment.TopEnd),
                    color = Color.White,
                    text = if (bookmarkCount > 9) "9+" else bookmarkCount.toString(),
                    style = MaterialTheme.typography.caption.copy(fontSize = 8.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

@Composable
fun BannerArticleItem(article: ArticleUiModel, onArticleSelect: (ArticleUiModel) -> Unit) {
    Column(modifier = Modifier.clickable { onArticleSelect(article) }) {
        RemoteImage(
            url = article.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = article.title,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onPrimary),
            modifier = Modifier.padding(8.dp)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = article.description,
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onPrimary),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }

}

@Preview("Main screen")
@Preview("Main screen (dark)", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        state = MainContract.State(true, flowOf(), 4),
        effectFlow = flowOf(MainContract.Effect.PullRefresh),
        onEventSent = {},
        onNavigationRequested = {},
        scaffoldState = rememberScaffoldState()
    )
}

@Preview("Article banner item")
@Preview("Article banner item (dark)", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ArticleBannerItemPreview(@PreviewParameter(ArticleUiModelProvider::class) article: ArticleUiModel) {
    BannerArticleItem(article, {})
}


@Preview("Article news")
@Preview("Article news (dark)", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ArticleItemPreview(@PreviewParameter(ArticleUiModelProvider::class) article: ArticleUiModel) {
    ArticleItem(article, {}, {})
}

@Preview("Badge Count")
@Composable
fun BookmarkBadgeCountPreview() {
    BookmarkBadgeCount(bookmarkCount = 90) {}
}