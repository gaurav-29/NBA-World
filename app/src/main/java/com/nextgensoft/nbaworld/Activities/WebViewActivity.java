package com.nextgensoft.nbaworld.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.nextgensoft.nbaworld.R;

public class WebViewActivity extends AppCompatActivity {
    WebView mywebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //getSupportActionBar().hide();

        mywebview = findViewById(R.id.webView);

        mywebview.getSettings().setLoadsImagesAutomatically(true);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.loadUrl("https://nbaworld.ngsoft.in/app/dashboard.php");
    }
}