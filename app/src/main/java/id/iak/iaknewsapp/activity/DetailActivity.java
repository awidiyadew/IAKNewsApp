package id.iak.iaknewsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import id.iak.iaknewsapp.model.ArticlesItem;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

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

    private void setupWebView(){
        webView.clearCache(true);
        webView.clearHistory();
        webView.setHorizontalScrollBarEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    String x = String.valueOf(scrollX);
                    String y = String.valueOf(scrollY);
                    Log.d("Scroll", "onScrollChange: x=" + x + " y=" + y);
                }
            });
        }

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
