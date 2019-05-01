package com.example.newsagregator.view;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newsagregator.R;
import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.network.NewsIntentService;
import com.example.newsagregator.presenter.INewsView;
import com.example.newsagregator.presenter.NewsNews;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements INewsView {
    private NewsNews newsPresenter;
    private RecyclerView NewsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ApplicationContextSingleton.setContext(this);
        newsPresenter = new NewsNews(this, Factory.createGetUseCaseImpl());
        loadRSS();


        //loadData();
    }

    private void loadData() {
        Intent intent = new Intent(MainActivity.this, NewsIntentService.class);
        startService(intent);
    }

    private void initViews() {
        NewsRecyclerView = findViewById(R.id.newsRecyclerView);
        NewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadRSS() {
      newsPresenter.updateNews();
    }

    @Override
    public void showNews(final List<NewsItem> listNewsItem) {

        NewsRecyclerView.setHasFixedSize(false);
        NewsAdapter newsAdapter = new NewsAdapter(listNewsItem);
        NewsRecyclerView.setAdapter(newsAdapter);
    }
}
