package id.daprin.iaknewsapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import id.daprin.iaknewsapp.R;
import id.daprin.iaknewsapp.model.ArticlesItem;

public class DetailActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_detail2);

        if (getIntent().hasExtra(KEY_EXTRA_NEWS)){
            String newsJson = getIntent().getStringExtra(KEY_EXTRA_NEWS);
            mArticlesItem = new ArticlesItem().fromJson(newsJson);
            Toast.makeText(this, "Show news " + mArticlesItem.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }

    }

}
