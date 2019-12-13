package com.example.photographer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class EquationActivity extends AppCompatActivity {

    WebView webView;
    String latexString;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation);

        Intent intent = this.getIntent();
        latexString = intent.getStringExtra("latex");

        webView = findViewById(R.id.mathWebView);

        /*
         * The app uses MathQuill, a javascript based latex editor
         * One may ask, why use a JS library when libraries like jLatexmath exist for java.
         * But, none of such libraries for java are WYSIWYG. They require LaTex to be hardcoded
         * or expect the user to enter LaTex. This app is intended to make data entry easier and
         * must be WYSIWYG. Though WebView is used, the app is completely offline.
         */

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webView.requestFocus(View.FOCUS_DOWN);
        webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + latexString);
    }
}
