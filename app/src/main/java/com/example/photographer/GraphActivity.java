package com.example.photographer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {

    WebView webView;
    String latexString;
    private TextView latexTextView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        webView = findViewById(R.id.graphWebView);
        latexTextView = findViewById(R.id.latexTextView);

        Intent intent = this.getIntent();
        latexString = intent.getStringExtra("latex");
        latexTextView.setText(latexString);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("about:blank");
        webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + latexString + "&mode=nokeyboard");
    }
}
