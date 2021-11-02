package com.vm.frameworkexample.components.main.email

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_auto_fill_web.*

@AndroidEntryPoint
open class AutoFillWebFragment : MainFragment<AutoFillWebViewModel>() {

    override val viewModelClassType: Class<AutoFillWebViewModel>
        get() = AutoFillWebViewModel::class.java

    override fun layoutRes(): Int {
        return R.layout.fragment_auto_fill_web
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailContentList.listAdapter.items = listOf("Hello", "World")

        emailContentList.setItemOnClickListener { item, itemView, itemIndex ->
            setClipboard(requireContext(), item)
        }

        val webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest
            ): Boolean {
                showLoading(true)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val  js = "javascript:" +
                        "document.getElementById('firstname').value = 'John';"  +
                        "document.getElementById('lastname').value = 'Doe';"  +
                        "document.getElementsByClassName('Link_pseudo')[0].click()"
//                webView.loadUrl(js)
            }
        }
        webView.webViewClient = webViewClient
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://passport.yandex.com/registration/mail")
    }

    private fun setClipboard(context: Context, text: String) {
        val clip = ClipData.newPlainText("Copied Text", text)
        val systemService = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        systemService.setPrimaryClip(clip)
    }
}
