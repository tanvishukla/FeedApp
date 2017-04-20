package com.personalcapitalchallenge.tanvi.feedapppersonalcapital;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleActivity extends AppCompatActivity {

    private WebView webView;
    private String articleLink, articleLinkExtras, articleTitle;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Webview Layout
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());

        //Extract Bundle data
        Bundle extras = getIntent().getExtras();
        articleLinkExtras = extras.getString("articleURL");
        articleTitle = extras.getString("articleTitle");
        articleLink = articleLinkExtras.concat("?displayMobileNavigation=0");

        //Show progress dialog while loading the web URL in web view
        progressDialog = new ProgressDialog(ArticleActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        webView.getSettings().setJavaScriptEnabled(true);
        setContentView(webView);

        //set article title on app bar
        android.support.v7.app.ActionBar toolbar= getSupportActionBar();
        toolbar.setTitle(articleTitle);

        //Load URL
        webView.loadUrl(articleLink);

        progressDialog.dismiss();

    }
}
