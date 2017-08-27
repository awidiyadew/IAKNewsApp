package id.iak.iaknewsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.iak.iaknewsapp.R;
import id.iak.iaknewsapp.database.DbOpenHelper;
import id.iak.iaknewsapp.model.ArticlesItem;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.webView) WebView mWebView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nestedScrollView) NestedScrollView nestedScrollView;
    @BindView(R.id.fabFavorite) FloatingActionButton fabFavorite;

    private static final String KEY_EXTRA_NEWS = "news";
    private ArticlesItem mArticlesItem;
    private boolean mIsNewsFavorite = false;
    private DbOpenHelper mDbHelper;

    // method untuk memulai DetailActivity
    public static void start(Context context, ArticlesItem articlesItem){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_EXTRA_NEWS, articlesItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mDbHelper = new DbOpenHelper(getApplicationContext());

        if (!getIntent().hasExtra(KEY_EXTRA_NEWS)){
            finish();
        }

        mArticlesItem = getIntent().getParcelableExtra(KEY_EXTRA_NEWS);
        setupWebView();
        mWebView.loadUrl(mArticlesItem.getUrl());
        progressBar.setMax(100);
        setupActionBar();
        setupFab();

    }

    private void setupWebView(){
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgressChanged: " + String.valueOf(newProgress));
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);

                super.onProgressChanged(view, newProgress);
            }
        });

        WebSettings webViewSetting = mWebView.getSettings();

        // enable zoom
        webViewSetting.setSupportZoom(true);
        webViewSetting.setBuiltInZoomControls(true);
        webViewSetting.setDisplayZoomControls(true);
        // enable javascript
        webViewSetting.setJavaScriptEnabled(true);
    }

    private void setupActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null){
            return;
        }

        actionBar.setTitle(mArticlesItem.getTitle());
        actionBar.setSubtitle(mArticlesItem.getUrl());

        // eanable home button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_action_close);
        actionBar.setHomeAsUpIndicator(drawable);
    }

    private void setupFab(){
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (oldScrollY > scrollY) {
                    fabFavorite.show();
                } else {
                    fabFavorite.hide();
                }
            }
        });

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIsNewsFavorite = mDbHelper.saveNewsItem(mArticlesItem);

                fabFavorite.setImageResource(mIsNewsFavorite ?
                        R.drawable.ic_action_love_full : R.drawable.ic_action_love_empty);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            // handling home button
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
