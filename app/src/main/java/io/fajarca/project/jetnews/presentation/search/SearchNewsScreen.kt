package io.fajarca.project.jetnews.presentation.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.list.NewsList
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchNewsScreen(viewModel: SearchNewsViewModel, onNavigationIconClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Search News") },
            elevation = 6.dp,
            navigationIcon = {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(Icons.Outlined.ArrowBack, null)
                }
            },
            actions = {
                SearchTextField(
                    uiState.text,
                    onTextChange = { text -> viewModel.onQueryChange(text) },
                    onSearchClick = { text -> viewModel.searchNews(text, "en") },
                    onClearQuery = { viewModel.onQueryChange("") }
                )
            }
        )

        if (uiState.isLoading) {
            Column(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        val context = LocalContext.current
        Text(
            text = "Recent searches",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        val keyboardController = LocalSoftwareKeyboardController.current
        SearchHistoryList(
            searchHistories = uiState.searchHistories,
            onChipSelect = {
                viewModel.onQueryChange(it.query)
                viewModel.searchNews(it.query, "en")
                keyboardController?.hide()
            }
        )

        NewsList(
            articles = uiState.searchResult.collectAsLazyPagingItems(),
            modifier = Modifier.weight(1f),
            onToggleBookmark = {},
            onHeadlineSelect = { headline ->
                NewsDetailActivity.start(
                    context,
                    headline.url
                )
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val textFieldValue = TextFieldValue(text = text, selection = TextRange(text.length))
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = textFieldValue,
        onValueChange = { newText -> onTextChange(newText.text) },
        shape = RoundedCornerShape(8.dp),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick(text)
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true,
        label = { Text(text = "Search..", color = MaterialTheme.colors.onPrimary) },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }

        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            cursorColor = MaterialTheme.colors.onPrimary,
            leadingIconColor = MaterialTheme.colors.onPrimary,
            trailingIconColor = MaterialTheme.colors.onPrimary,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
fun SearchHistoryList(searchHistories: List<SearchHistory>, onChipSelect: (SearchHistory) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(searchHistories) {
            Chip(history = it, onChipSelect = onChipSelect)
        }
    }
}

@Composable
fun Chip(history: SearchHistory, onChipSelect: (SearchHistory) -> Unit) {
    Surface(
        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
        elevation = 8.dp,
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f)
        ),
        content = {
            Text(
                text = history.query, modifier = Modifier
                    .clickable { onChipSelect(history) }
                    .padding(8.dp)
                    .defaultMinSize(24.dp, 24.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ChipPreview() {
    Chip(SearchHistory(1, "Aston Martin", Date()), {})
}


@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    SearchTextField(
        "Aston Martin",
        onTextChange = {},
        onSearchClick = {},
        onClearQuery = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarWithSearchViewPreview() {
    TopAppBar(
        title = {},
        elevation = 6.dp,
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Outlined.ArrowBack, null)
            }
        },
        actions = {

            SearchTextField(
                "Aston Martin",
                onTextChange = {},
                onSearchClick = {},
                onClearQuery = {}
            )


        }
    )
}