package com.bw.zhuguiquan20191126;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TextActivity extends BaseActivity {


    private WebView w;
    private String key;

    @Override
    protected void initData() {
        //获取数据
        Intent intent = getIntent();
        //设置数据
        key = intent.getStringExtra("key");
        w.loadUrl(key);
        WebSettings settings = w.getSettings();
        settings.setJavaScriptEnabled(true);
        w.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(key);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("xxx","开始");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("xxx","结束");
            }
        });
        w.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("xxx","onProgressChanged"+newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i("xxx","onProgressChanged"+title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                Log.i("xxx","onReceivedIcon"+icon);
            }
        });

    }

    @Override
    protected void initView() {
        w = findViewById(R.id.web);

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_text;
    }
}
