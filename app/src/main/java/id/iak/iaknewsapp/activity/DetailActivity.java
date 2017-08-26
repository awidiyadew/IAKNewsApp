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
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.iak.iaknewsapp.R;
import id.iak.iaknewsapp.local.DbOpenHelper;
import id.iak.iaknewsapp.model.ArticlesItem;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.fabFavorite) FloatingActionButton fabFavorite;
    @BindView(R.id.nestedScrollView) NestedScrollView nestedScrollView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private static final String KEY_EXTRA_NEWS = "news";
    private ArticlesItem mArticlesItem;
    private boolean mIsNewsSaved = false;
    private DbOpenHelper mDbHelper;

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

        /* jika activity di start tanpa membawa data news, maka finish activity*/
        if (!getIntent().hasExtra(KEY_EXTRA_NEWS)){
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        String newsJson = getIntent().getStringExtra(KEY_EXTRA_NEWS);
        mArticlesItem = new ArticlesItem().fromJson(newsJson);

        setupWebView();
        webView.loadUrl(mArticlesItem.getUrl());

        setupActionBar();
        setupFab();

        // database operation
        mDbHelper = new DbOpenHelper(getApplicationContext());
        mIsNewsSaved = mDbHelper.isNewsSavedAsFavorite(mArticlesItem.getUrl());
        if (mIsNewsSaved){
            // news saved as favorite
            Toast.makeText(this, "Saved as favorite", Toast.LENGTH_SHORT).show();
            fabFavorite.setImageResource(R.drawable.ic_action_love_full);
        } else {
            Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
            fabFavorite.setImageResource(R.drawable.ic_action_love_empty);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(R.string.actiobar_detail_title);
            actionBar.setSubtitle(mArticlesItem.getUrl());

            // mengaktifkan back button
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            // setup close icon
            Drawable backDrawable = ContextCompat.getDrawable(this, R.drawable.ic_action_close);
            actionBar.setHomeAsUpIndicator(backDrawable);
        }
    }

    private void setupFab(){
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY){
                    if (fabFavorite.isShown()){
                        fabFavorite.hide();
                    }
                } else {
                    if (!fabFavorite.isShown()){
                        fabFavorite.show();
                    }
                }
            }
        });

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsNewsSaved){
                    boolean isDeleteSuccess = mDbHelper.deleteNewsItem(mArticlesItem.getUrl());
                    mIsNewsSaved = !isDeleteSuccess;
                } else {
                    mIsNewsSaved = mDbHelper.saveNewsItem(mArticlesItem) > 0;
                }

                fabFavorite.setImageResource(mIsNewsSaved ? R.drawable.ic_action_love_full : R.drawable.ic_action_love_empty);
            }
        });
    }

    private void setupWebView(){
        webView.clearCache(true);
        webView.clearHistory();
        webView.setHorizontalScrollBarEnabled(false);

        progressBar.setMax(100);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
            }
        });

        WebSettings webViewSetting = webView.getSettings();

        // enable zoom
        webViewSetting.setSupportZoom(true);
        webViewSetting.setBuiltInZoomControls(true);
        webViewSetting.setDisplayZoomControls(true);
        // enable javascript
        webViewSetting.setJavaScriptEnabled(true);
    }

}
