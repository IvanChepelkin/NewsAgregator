package com.example.newsagregator.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.newsagregator.model.NewsRssObject;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    private NewsRssObject newsRssObject;

    public NewsAdapter(NewsRssObject newsRssObject) {
        this.newsRssObject = newsRssObject;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return NewsHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
        newsHolder.bind(newsRssObject, i);

    }

    @Override
    public int getItemCount() {
        return newsRssObject.getItems().size();
    }
}
