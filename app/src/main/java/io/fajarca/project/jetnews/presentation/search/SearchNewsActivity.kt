package io.fajarca.project.jetnews.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme

@AndroidEntryPoint
class SearchNewsActivity : AppCompatActivity() {
    private val viewModel: SearchNewsViewModel by viewModels()

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SearchNewsActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNewsTheme {
                SearchNewsScreen(viewModel)
            }
        }
    }
}