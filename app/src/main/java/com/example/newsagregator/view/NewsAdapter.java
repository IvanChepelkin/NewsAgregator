package com.example.newsagregator.view;

import com.example.newsagregator.model.domain.NewsEmptity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
    private List<NewsEmptity> listNewsEmptity;

    public NewsAdapter(List<NewsEmptity> listNewsEmptity) {
        this.listNewsEmptity = listNewsEmptity;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return NewsHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
        newsHolder.bind(listNewsEmptity.get(i));

    }

    @Override
    public int getItemCount() {
        return listNewsEmptity.size();
    }
}
