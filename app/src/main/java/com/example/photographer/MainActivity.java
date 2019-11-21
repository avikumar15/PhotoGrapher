package com.example.photographer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton cameraButton;
    Button graphIt;
    WebView webView;
    String latexString;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = findViewById(R.id.cameraButton);
        graphIt = findViewById(R.id.graphIt);
        webView = findViewById(R.id.mathWebView);
        latexString = "\\\\frac{-b\\\\pm\\\\sqrt{b^2-4ac}}{2a}";

        /*
         * The app uses MathQuill, a javascript based latex editor
         * One may ask, why use a JS library when libraries like jLatexmath exist for java.
         * But, none of such libraries for java are WYSIWYG. They require LaTex to be hardcoded
         * or expect the user to enter LaTex. This app is intended to make data entry easier and
         * must be WYSIWYG. Though WebView is used, the app is completely offline.
         */

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.requestFocus(View.FOCUS_DOWN);
        webView.loadUrl("file:///android_asset/math_text_box.html");
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCameraButtonClick();
            }
        });
    }

    private void onCameraButtonClick() {
        /*
         * For the time being let the string 'latexString' be returned from the API that retrieves
         * the math equations from the image. Now, we have to pass this into the webView and update
         * the contents of the math input box. This is demonstrated by passing a test LaTex string
         * to the webView.
         */
        webView.loadUrl("javascript:updateLatex(\"" + latexString + "\")");
        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(intent);
    }
}
