package com.moac.android.myrssreader.ui.article;

import com.moac.android.myrssreader.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public final class ArticleActivity extends AppCompatActivity {

    private static final String ARTICLE_URL_KEY = "ARTICLE_URL";

    public static void launch(final Context context, final String articleUrl) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(ARTICLE_URL_KEY, articleUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(final Intent intent) {
        // Retrieve the Article URL from the Intent parameters
        String url = intent.getStringExtra(ARTICLE_URL_KEY);

        // Load the URL into the WebView
        WebView webView = (WebView) findViewById(R.id.webview_article);
        webView.loadUrl(url);
    }
}
