package com.appplepie.maskstock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MoreInfoWebView extends WebView {
    public MoreInfoWebView(Context context) {
        super(context);
    }

    public MoreInfoWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoreInfoWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MoreInfoWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initDefaultSetting(){
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new InfoWebViewClient());
    }
}
