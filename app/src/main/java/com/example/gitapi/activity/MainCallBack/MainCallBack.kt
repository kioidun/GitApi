package com.example.gitapi.activity.MainCallBack

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.gitapi.R
import com.example.gitapi.activity.UserActivity.UserActivity

class MainCallBack : AppCompatActivity() {
    lateinit var mWebView: WebView
    lateinit var kioi: String
    private lateinit var extras: Bundle
    private lateinit var newString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_call_back)

        mWebView = findViewById(R.id.webviewid)

        extras = intent.extras!!
        newString = extras.getString("STRING_I_NEED").toString()

        mWebView.settings.javaScriptEnabled = true
        mWebView.loadUrl("https://github.com/login/oauth/authorize?client_id=ea39d14d748eadf55859&redirect_uri=https://kioigitapi.herokuapp.com/callback")
        mWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                var webviewUrl = url

            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                var webviewUrl = url
                if (webviewUrl!!.contains("https://kioigitapi.herokuapp.com/callback")) {
                    val url = Uri.parse(webviewUrl)
                    val paramNames = url.getQueryParameterNames()
                    for (key in paramNames) {
                        val value = url.getQueryParameter(key)
                        kioi = value.toString()

                    }
                    val i = Intent(this@MainCallBack, UserActivity::class.java)
                    i.putExtra("Code", kioi)
                    i.putExtra("STRING_I_NEED", newString.toString())
                    startActivity(i)
                    return true
                }
                return false

            }
        }
    }
}
