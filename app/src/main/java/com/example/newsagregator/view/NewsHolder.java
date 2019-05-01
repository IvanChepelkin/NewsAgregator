package com.example.newsagregator.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.newsagregator.R;
import com.example.newsagregator.model.domain.NewsItem;

class NewsHolder extends RecyclerView.ViewHolder {
    private int id;
    private NewsItem newsItem;
    private View root;
    private TextView textTitle;
    private TextView textContent;
    private WebView webViewContent;

    private NewsHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView;
        textTitle = itemView.findViewById(R.id.newsItemTitle);
        textContent = itemView.findViewById(R.id.newsItemContent);
        webViewContent = itemView.findViewById(R.id.newsItemGuide);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webViewContent.getSettings().setJavaScriptEnabled(true);
                webViewContent.loadUrl(newsItem.getGuide());
            }
        });
    }

    void bind(NewsItem newsItem) {
        this.newsItem = newsItem;
        textTitle.setText(newsItem.getTitle());
        textContent.setText(newsItem.getContent());

    }

    static NewsHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new NewsHolder(inflater.inflate(R.layout.news_card_item, parent, false));
    }
}
