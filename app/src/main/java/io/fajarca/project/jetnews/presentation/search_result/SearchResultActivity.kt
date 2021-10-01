package io.fajarca.project.jetnews.presentation.search_result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme

@AndroidEntryPoint
class SearchResultActivity : ComponentActivity() {

    private var query = ""
    private val viewModel : SearchResultViewModel by viewModels()

    companion object {
        private const val INTENT_KEY_QUERY = "query"

        @JvmStatic
        fun start(context: Context, query: String) {
            val starter = Intent(context, SearchResultActivity::class.java)
                .putExtra(INTENT_KEY_QUERY, query)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntentArguments()
        viewModel.searchNews(query,  "en")

        setContent {
            JetNewsTheme {
                SearchResultScreen(query, viewModel) { finish() }
            }
        }
    }

    private fun handleIntentArguments() {
        query = intent.extras?.getString(INTENT_KEY_QUERY) ?: return
    }
}
