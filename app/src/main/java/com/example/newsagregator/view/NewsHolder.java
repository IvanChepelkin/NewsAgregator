package com.example.newsagregator.view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.newsagregator.R;
import com.example.newsagregator.model.NewsRssObject;

class NewsHolder extends RecyclerView.ViewHolder {
    private int id;
    private NewsRssObject newsRssObject;
    private View root;
    private TextView textTitle;
    private TextView textContent;
    private WebView webViewContent;

    private NewsHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView;
        textTitle = itemView.findViewById(R.id.txtTitle);
        textContent = itemView.findViewById(R.id.txtContent);
        webViewContent = itemView.findViewById(R.id.webViewContent);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webViewContent.getSettings().setJavaScriptEnabled(true);
                webViewContent.loadUrl(newsRssObject.getItems().get(id).getGuid());
            }
        });


    }

    void bind(NewsRssObject newsRssObject, int id) {
        this.id = id;
        this.newsRssObject = newsRssObject;
        textTitle.setText(newsRssObject.getItems().get(id).getTitle());
        textContent.setText(newsRssObject.getItems().get(id).getContent());

    }

    static NewsHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new NewsHolder(inflater.inflate(R.layout.card_item_news, parent, false));
    }
}
