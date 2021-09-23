package io.fajarca.project.jetnews.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import io.fajarca.project.jetnews.presentation.JetNewsApp

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
            MaterialTheme {
                NewsDetailScreen(url = url)
            }
        }
    }

    private fun handleIntentArguments() {
        url = intent.extras?.getString(INTENT_KEY_URL) ?: return
    }
}