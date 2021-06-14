package com.example.firebased.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.firebased.R

class WebFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_web, container, false)
        val url: String = arguments?.getString("url").toString()
        view.findViewById<WebView>(R.id.webView).webViewClient = WebViewClient()
        view.findViewById<WebView>(R.id.webView).loadUrl(url)
        return view
    }
}