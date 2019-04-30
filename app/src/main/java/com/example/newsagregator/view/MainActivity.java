package com.example.newsagregator.view;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newsagregator.R;
import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.network.HttpIntentService;
import com.example.newsagregator.presenter.INewsView;
import com.example.newsagregator.presenter.NewsPresenter;
import com.example.newsagregator.model.domain.NewsEmptity;
import com.example.newsagregator.view.NewsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements INewsView {
    NewsPresenter newsPresenter;
    RecyclerView recViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ApplicationContextSingleton.setContext(this);
        newsPresenter = new NewsPresenter(this, Factory.createGetUseCaseImpl());
        loadRSS();
        loadData();
    }

    private void loadData() {
        Intent intent = new Intent(MainActivity.this, HttpIntentService.class);
        startService(intent);
    }

    private void initViews() {
        recViewNews = findViewById(R.id.recViewNews);
        recViewNews.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadRSS() {
      newsPresenter.getDataForView();
    }

    @Override
    public void showNews(List<NewsEmptity> listNewsEmptity) {

        recViewNews.setHasFixedSize(false);
        NewsAdapter newsAdapter = new NewsAdapter(listNewsEmptity);
        recViewNews.setAdapter(newsAdapter);
    }
}