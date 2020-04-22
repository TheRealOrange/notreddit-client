package com.therealorange.notreddit

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webview.settings.javaScriptEnabled=true
        webview.webViewClient = WebViewClient()
        webview.loadUrl(intent.getStringExtra(EXTRA_URL))
    }

    companion object {
        const val EXTRA_URL = "extra.url"
    }
}