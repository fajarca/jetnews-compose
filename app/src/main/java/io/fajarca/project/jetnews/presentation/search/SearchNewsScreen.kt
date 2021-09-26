package io.fajarca.project.jetnews.presentation.search

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import io.fajarca.project.jetnews.presentation.detail.NewsDetailActivity
import io.fajarca.project.jetnews.presentation.list.NewsList

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
                    onSearchClick = { text -> viewModel.searchNews(text, "en") }
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
        NewsList(
            topHeadlines = uiState.searchResult.collectAsLazyPagingItems(),
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

@Composable
fun SearchTextField(text: String, onTextChange: (String) -> Unit, onSearchClick: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
        value = text,
        onValueChange = { newText -> onTextChange(newText) },
        shape = RoundedCornerShape(8.dp),
        keyboardActions = KeyboardActions { onSearchClick(text) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Default.Search, null,
                Modifier
                    .padding(16.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { onTextChange("") }) {
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
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable()
fun SearchTextFieldPreview() {
    //SearchTextField()
}