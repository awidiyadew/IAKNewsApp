package id.daprin.iaknewsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.daprin.iaknewsapp.R;
import id.daprin.iaknewsapp.adapter.NewsAdapter;
import id.daprin.iaknewsapp.model.ArticlesItem;

public class MainActivity extends AppCompatActivity {

    /*Recycler view perlu: VIewHolder, Adapter, LayoutManager */
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        //SETUP ADAPTER
        mAdapter = new NewsAdapter(GetDummyArticlesItem());

        //SETUP RECYCLERVIEW
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<ArticlesItem> GetDummyArticlesItem(){
        List<ArticlesItem> result = new ArrayList<>();
        for(int i=0; i<10; i++){
            ArticlesItem item = new ArticlesItem();
            item.setTitle("Ini Merupakakan Title untuk menampilkan max line dari textview");
            item.setDescription("Ini merupakan deskripsi yang merupakan data random, yang bisa di copas. Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.");
            item.setUrlToImage("https://tctechcrunch2011.files.wordpress.com/2017/08/aug_chart_1.png?w=764&h=400&crop=1");
            result.add(item);
        }
        return result;
    }
}
