package com.example.newsagregator.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.newsagregator.R;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

class NewsHolder extends RecyclerView.ViewHolder {
    private NewsItem newsItem;
    private View root;
    private TextView textNewsChannelName;
    private TextView textDatePublication;
    private TextView textTitle;
    private TextView textContent;
    private NewsAdapter.ItemListener itemListener;
    private int position;
    private NewsHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView;
        textNewsChannelName = itemView.findViewById(R.id.newsChannelName);
        textDatePublication = itemView.findViewById(R.id.datePublication);
        textTitle = itemView.findViewById(R.id.newsItemTitle);
        textContent = itemView.findViewById(R.id.newsItemContent);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onItemClick(position);
            }
        });
    }

    void bind(NewsItem newsItem, NewsAdapter.ItemListener itemListener, int position) {
        this.newsItem = newsItem;
        this.itemListener = itemListener;
        this.position = position;
        textNewsChannelName.setText(newsItem.getChannelName());
        textDatePublication.setText(newsItem.getDatePublication());
        textTitle.setText(newsItem.getTitle());
        textContent.setText(newsItem.getContent());


    }

    static NewsHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new NewsHolder(inflater.inflate(R.layout.news_card_item, parent, false));
    }
}
