package id.iak.iaknewsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.iak.iaknewsapp.R;
import id.iak.iaknewsapp.model.ArticlesItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private List<ArticlesItem> articlesItemList;
    private NewsClickListener mNewsClickListener;

    public NewsAdapter(List<ArticlesItem> articlesItemList) {
        this.articlesItemList = articlesItemList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news,parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        holder.bind(articlesItemList.get(position));
        holder.btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNewsClickListener != null){
                    mNewsClickListener.onItemNewsClicked(
                            articlesItemList.get(position)
                    );
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return articlesItemList.size();
    }

    public void setData(List<ArticlesItem> datas){
        this.articlesItemList.clear();
        articlesItemList.addAll(datas);
        notifyDataSetChanged();
    }

    public void setItemClickListener(NewsClickListener clickListener){
        if (clickListener != null){
            mNewsClickListener = clickListener;
        }
    }

    //View Holder untuk adapter
    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ivNewsPhoto) ImageView ivNewsPhoto;
        @BindView(R.id.tvDescription) TextView tvDescription;
        @BindView(R.id.tvNewsTitle) TextView tvNewsTitle;
        @BindView(R.id.btnReadMore) Button btnReadMore;

        public NewsViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(ArticlesItem newsItem){
            tvNewsTitle.setText(newsItem.getTitle());
            tvDescription.setText(newsItem.getDescription());
            Glide.with(ivNewsPhoto.getContext())
                    .load(newsItem.getUrlToImage())
                    .into(ivNewsPhoto);

        }
    }
}

