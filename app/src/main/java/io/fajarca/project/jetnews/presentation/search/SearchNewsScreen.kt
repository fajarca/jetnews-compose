package io.fajarca.project.jetnews.presentation.search

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.fajarca.project.jetnews.domain.entity.SearchHistory
import io.fajarca.project.jetnews.presentation.search_result.SearchResultActivity
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme
import io.fajarca.project.jetnews.util.preview.SearchHistoryProvider
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchNewsScreen(viewModel: SearchNewsViewModel, onNavigationIconClick: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchNewsScreen(
        searchHistories = uiState.searchHistories,
        text = uiState.text,
        onNavigationIconClick = onNavigationIconClick,
        onItemSelect = {
            viewModel.onQueryChange(it.query)
            SearchResultActivity.start(context, it.query)
            viewModel.recordSearchHistory(it.query)
            keyboardController?.hide()
        },
        onClearSearchHistory = { viewModel.clearSearchHistory() },
        onTextChange = { text -> viewModel.onQueryChange(text) },
        onSearchClick = { text ->
            SearchResultActivity.start(context, text)
            viewModel.recordSearchHistory(text)
        },
        onClearQuery = { viewModel.onQueryChange("") }
    )
}

@Composable
fun SearchNewsScreen(
    searchHistories: List<SearchHistory>,
    text: String,
    onNavigationIconClick: () -> Unit,
    onItemSelect: (SearchHistory) -> Unit,
    onClearSearchHistory: () -> Unit,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onClearQuery: () -> Unit
) {
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
                    text,
                    onTextChange = onTextChange,
                    onSearchClick = onSearchClick,
                    onClearQuery = onClearQuery
                )
            }
        )

        if (searchHistories.isNotEmpty()) {
            RecentSearchSection(
                onClearSearchHistory = onClearSearchHistory
            )
        }

        SearchHistoryList(
            searchHistories = searchHistories,
            onItemSelect = onItemSelect
        )
    }
}

@Composable
fun RecentSearchSection(onClearSearchHistory: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Recent searches",
            style = MaterialTheme.typography.subtitle2.copy(color = MaterialTheme.colors.onPrimary)
        )
        Text(
            text = "Clear",
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onPrimary),
            modifier = Modifier.clickable { onClearSearchHistory() }
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
fun SearchHistoryList(searchHistories: List<SearchHistory>, onItemSelect: (SearchHistory) -> Unit) {
    LazyColumn {
        items(searchHistories) {
            SearchHistoryItem(history = it, onItemClick = onItemSelect)
        }
    }
}

@Composable
fun SearchHistoryItem(history: SearchHistory, onItemClick: (SearchHistory) -> Unit) {
    Text(
        text = history.query,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(history) }
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onPrimary)
    )
}


@Preview("Search news screen", showBackground = true)
@Preview("Search news (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SearchNewsScreenPreview() {
    JetNewsTheme {
        SearchNewsScreen(
            searchHistories = listOf(SearchHistory("Aston Martin", Date())),
            text = "Some search..",
            onNavigationIconClick = {},
            onItemSelect = {},
            onClearSearchHistory = {},
            onTextChange = {},
            onSearchClick = {},
            onClearQuery = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecentSearchSectionPreview(@PreviewParameter(SearchHistoryProvider::class) history: SearchHistory) {
    RecentSearchSection(onClearSearchHistory = {})
}

@Preview(showBackground = true)
@Composable
fun SearchHistoryItemPreview(@PreviewParameter(SearchHistoryProvider::class) history: SearchHistory) {
    SearchHistoryItem(history, {})
}


@Preview(showBackground = true)
@Composable
fun AppBarWithSearchViewPreview() {
    JetNewsTheme {
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
}

