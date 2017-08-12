package id.daprin.iaknewsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.daprin.iaknewsapp.R;

public class TesActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView mWebView;

    private static final String URL = "https://techcrunch.com/2017/08/11/uber-shareholder-group-asks-benchmark-to-step-down-from-board-following-kalanick-suit/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setupWebView();
        mWebView.loadUrl(URL);
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
