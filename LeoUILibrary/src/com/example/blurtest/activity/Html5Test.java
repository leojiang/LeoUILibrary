package com.example.blurtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.blurtest.R;

public class Html5Test extends Activity {

    private WebView mWebView;
//    private String mUrl = "http://www.17sucai.com/preview/11/2014-12-03/nav/demo.html";
    private String mUrl = "file:///android_asset/test.htm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html5_test);


        mWebView = (WebView) findViewById(R.id.webView1);
        initWebView(mWebView);
    }

    public void initWebView(WebView mWebView) {
        if (TextUtils.isEmpty(mUrl)) {
            return;
        }

        mWebView.getSettings().setJavaScriptEnabled(true); // 设置支持javascript脚本
        mWebView.getSettings().setBuiltInZoomControls(false);// 支持缩放

        mWebView.getSettings()
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setHorizontalScrollBarEnabled(false);// 水平不显示
        mWebView.setVerticalScrollBarEnabled(false); // 垂直不显示

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebView.loadUrl(mUrl);
    }
}
