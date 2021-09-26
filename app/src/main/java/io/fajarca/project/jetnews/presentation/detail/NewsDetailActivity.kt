package io.fajarca.project.jetnews.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import io.fajarca.project.jetnews.ui.theme.JetNewsTheme

class NewsDetailActivity : AppCompatActivity() {

    private var url = ""

    companion object {
        private const val INTENT_KEY_URL = "url"

        @JvmStatic
        fun start(context: Context, url: String) {
            val starter = Intent(context, NewsDetailActivity::class.java)
                .putExtra(INTENT_KEY_URL, url)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntentArguments()
        setContent {
            JetNewsTheme {
                NewsDetailScreen(url = url, onNavigationIconClick = { finish() })
            }
        }
    }

    private fun handleIntentArguments() {
        url = intent.extras?.getString(INTENT_KEY_URL) ?: return
    }
}