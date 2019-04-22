package com.example.newsagregator.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.newsagregator.R;
import com.example.newsagregator.model.network.models.NewsRssObject;
import com.example.newsagregator.presenter.model_view.ModelView;

class NewsHolder extends RecyclerView.ViewHolder {
    private int id;
    private ModelView modelView;
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
                webViewContent.loadUrl(modelView.getGuide());
            }
        });
    }

    void bind(ModelView modelView) {
        this.id = id;
        this.modelView = modelView;
        textTitle.setText(modelView.getTitle());
        textContent.setText(modelView.getContent());

    }

    static NewsHolder create(LayoutInflater inflater, ViewGroup parent) {
        return new NewsHolder(inflater.inflate(R.layout.card_item_news, parent, false));
    }
}
