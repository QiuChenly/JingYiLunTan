package com.qiuchen.View

import android.content.ClipboardManager
import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.qiuchen.Base.BaseApp
import com.qiuchen.Base.mLayoutSet
import com.qiuchen.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebView : BaseApp() {
    lateinit var Url: String
    override fun InitOver() {
        Url = this.intent.getStringExtra("url")

        mWebView.setWebViewClient(
                object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        view.loadUrl(url)
                        return true
                    }
                })
        mWebView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            domStorageEnabled = true
        }

        mWebView.loadUrl(Url)
        more.setOnClickListener(this)
    }

    override fun getSet(mSet: mLayoutSet): mLayoutSet {
        return mSet.apply {
            this.layout = R.layout.activity_web_view
            doubleClickExit = false
        }
    }

    override fun onPressBackKey(): Boolean {
        return false
    }

    override fun onClick(p0: View?) {
        when (p0) {
            more -> {
                onKeyUp(KeyEvent.KEYCODE_MENU, null)
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            val dialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.menu_task, null)
            val mRefresh = view.findViewById(R.id.mRefresh)
            val mCopyURL = view.findViewById(R.id.mCopyURL)

            mCopyURL.setOnClickListener({ view: View ->
                val cp = view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                cp.text = Url
                dialog.dismiss()
                Toast.makeText(view.context, "复制数据到剪切板成功！", Toast.LENGTH_SHORT)
                        .show()
            })
            mRefresh.setOnClickListener({
                mWebView.loadUrl(Url)
                dialog.dismiss()
            })
            dialog.setContentView(view)
            dialog.show()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }
}
