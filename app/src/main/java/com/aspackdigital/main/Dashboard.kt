package com.aspackdigital.main

import android.annotation.TargetApi
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.aspackdigital.R

class Dashboard : AppCompatActivity() {
    internal lateinit var wvPage1: WebView
    internal lateinit var proWeb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        proWeb = findViewById<View>(R.id.proWeb) as ProgressBar
        proWeb.setVisibility(View.VISIBLE)
        wvPage1 = findViewById<View>(R.id.webView) as WebView
        wvPage1.loadUrl("http://software.digitalindiaonline.org/login")
        val settings = wvPage1.getSettings()
        settings.setJavaScriptEnabled(true)
        if (Build.VERSION.SDK_INT >= 19) {
            wvPage1.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
        else {
            wvPage1.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        try {
            wvPage1.webViewClient = MyWebViewClient()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        wvPage1.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                proWeb.setProgress(newProgress)
                if (newProgress == 100) {
                    proWeb.setVisibility(View.GONE)
                }
                super.onProgressChanged(view, newProgress)
            }
        })
        wvPage1.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }
        }
    }
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return true
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ConfirmExit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun ConfirmExit() {
        val alertDialog = AlertDialog.Builder(this@Dashboard)
        alertDialog.setTitle("Leave application?")
        alertDialog.setMessage("Are you sure you want to leave the application?")
        alertDialog.setIcon(R.drawable.ic_launcher)
        alertDialog.setPositiveButton("YES"
        ) { alertDialog, which -> finish() }
        alertDialog.setNegativeButton("NO") { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

}