package com.example.photographer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EquationActivity extends AppCompatActivity {

    WebView webView;
    String latexString;
    Button graphIt;
    ProgressBar progressBar;
    FrameLayout progressContainer;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation);

        Intent intent = this.getIntent();
        latexString = intent.getStringExtra("latex");

        webView = findViewById(R.id.mathWebView);
        graphIt = findViewById(R.id.graphit);
        progressBar = findViewById(R.id.progress_circular);
        progressContainer = findViewById(R.id.progress_container);

        graphIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphItClick();
            }
        });

        /*
         * The app uses MathQuill, a javascript based latex editor
         * One may ask, why use a JS library when libraries like jLatexmath exist for java.
         * But, none of such libraries for java are WYSIWYG. They require LaTex to be hardcoded
         * or expect the user to enter LaTex. This app is intended to make data entry easier and
         * must be WYSIWYG. Though WebView is used, the app is completely offline.
         */

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressContainer.animate().alpha(0).setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
                    webView.animate().alpha(1).setDuration(500).setInterpolator(new AccelerateInterpolator()).start();
                } else {
                    progressContainer.setAlpha(1);
                    webView.setAlpha(0);
                }
            }
        });
        webView.setWebViewClient(new MyWebViewClient());

        try {
            webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + URLEncoder.encode(latexString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.evaluateJavascript("getLatex()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                updateLatex(value);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + URLEncoder.encode(latexString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.loadUrl("about:blank");
    }

    private void graphItClick() {
        final Intent intent = new Intent(this, GraphActivity.class);

        //JavaScript is evaluated to retrieve the LaTeX from webView
        webView.evaluateJavascript("getLatex()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                updateLatex(value);
                intent.putExtra("latex", latexString);
                startActivity(intent);
            }
        });
    }

    private void updateLatex(String value) {
        latexString = value.replaceAll("\"", "")
                .replace("\\\\", "\\")
                .replace("\\u003C", "<");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
