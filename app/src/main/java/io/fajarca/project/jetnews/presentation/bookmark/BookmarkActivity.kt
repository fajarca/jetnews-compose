package io.fajarca.project.jetnews.presentation.bookmark

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme

@AndroidEntryPoint
class BookmarkActivity : ComponentActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, BookmarkActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNewsTheme {
                BookmarkScreen(hiltViewModel()) { finish() }
            }
        }
    }

}
