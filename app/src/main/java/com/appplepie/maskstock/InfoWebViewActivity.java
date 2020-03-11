package com.appplepie.maskstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class InfoWebViewActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_web_view);
        webView = findViewById(R.id.infoWebView);
        Intent intent = getIntent();
        webView.loadUrl(intent.getStringExtra("url"));
        Log.e("tag", "onCreate: "+intent.getStringExtra("url") );

    }
}
