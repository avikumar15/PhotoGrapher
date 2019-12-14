package com.example.photographer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EquationActivity extends AppCompatActivity {

    WebView webView;
    String latexString;
    Button graphIt;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation);

        Intent intent = this.getIntent();
        latexString = intent.getStringExtra("latex");

        webView = findViewById(R.id.mathWebView);
        graphIt = findViewById(R.id.graphit);

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
        webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + latexString);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + latexString);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webView.loadUrl("about:blank");
    }

    private void graphItClick() {
        final Intent intent = new Intent(this, GraphActivity.class);
        webView.evaluateJavascript("getLatex()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                latexString = value.replaceAll("\"", "").replace("\\\\", "\\");
                intent.putExtra("latex", latexString);
                startActivity(intent);
            }
        });
    }


}
