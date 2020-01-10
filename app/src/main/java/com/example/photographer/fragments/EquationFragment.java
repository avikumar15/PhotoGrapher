package com.example.photographer.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.photographer.R;
import com.example.photographer.activities.GraphActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class EquationFragment extends Fragment {
    WebView webView;
    String latexString = "";
    Button graphIt;
    ProgressBar progressBar;
    FrameLayout progressContainer;
    public EquationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equation, container, false);
        webView = view.findViewById(R.id.mathWebView);
        graphIt = view.findViewById(R.id.graphit);
        progressBar = view.findViewById(R.id.progress_circular);
        progressContainer = view.findViewById(R.id.progress_container);

        graphIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphItClick();
            }
        });
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
        webView.setWebViewClient(new EquationFragment.MyWebViewClient());

        try {
            webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + URLEncoder.encode(latexString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.evaluateJavascript("getLatex()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                updateLatex(value);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            webView.loadUrl("file:///android_asset/math_text_box.html?latex=" + URLEncoder.encode(latexString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        webView.loadUrl("about:blank");
    }

    private void graphItClick() {
        final Intent intent = new Intent(getActivity(), GraphActivity.class);

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
