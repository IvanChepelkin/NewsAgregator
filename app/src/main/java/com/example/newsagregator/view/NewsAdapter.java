package com.example.newsagregator.view;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    private List<NewsItem> listNewsItem;
    private ItemListener itemListener;

    public NewsAdapter(ItemListener itemListener) {
        super();
        this.itemListener = itemListener;

    }

    public void setListNewsItem(List<NewsItem> listNewsItem){
        this.listNewsItem = listNewsItem;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return NewsHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int position) {
        newsHolder.bind(listNewsItem.get(position), itemListener, position);

    }

    @Override
    public int getItemCount() {
        return listNewsItem.size();
    }

    public interface ItemListener {
        void onItemClick(int id, WebView webViewContent);
    }
}
