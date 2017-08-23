package id.iak.iaknewsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.iak.iaknewsapp.R;
import id.iak.iaknewsapp.model.ArticlesItem;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.webView) WebView mWebView;

    private static final String KEY_EXTRA_NEWS = "news";
    private ArticlesItem mArticlesItem;

    // method untuk memulai DetailActivity
    public static void start(Context context, String newsJson){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_EXTRA_NEWS, newsJson);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(KEY_EXTRA_NEWS)){
            String newsJson = getIntent().getStringExtra(KEY_EXTRA_NEWS);
            mArticlesItem = new ArticlesItem().fromJson(newsJson);
            Toast.makeText(this, "Show news " + mArticlesItem.getTitle(), Toast.LENGTH_SHORT).show();
            setupWebView();
            mWebView.loadUrl(mArticlesItem.getUrl());
        } else {
            finish();
        }

    }

    private void setupWebView(){
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setHorizontalScrollBarEnabled(false);

        WebSettings webViewSetting = mWebView.getSettings();

        // enable zoom
        webViewSetting.setSupportZoom(true);
        webViewSetting.setBuiltInZoomControls(true);
        webViewSetting.setDisplayZoomControls(true);
        // enable javascript
        webViewSetting.setJavaScriptEnabled(true);
    }

}
