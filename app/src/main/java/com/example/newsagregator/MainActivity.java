package com.example.newsagregator;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.newsagregator.model.DataManager;
import com.example.newsagregator.model.network.models.NewsRssObject;
import com.example.newsagregator.presenter.INewsView;
import com.example.newsagregator.presenter.NewsPresenter;
import com.example.newsagregator.presenter.model_view.ModelView;
import com.example.newsagregator.service.TestHTTPConnection;
import com.example.newsagregator.view.NewsAdapter;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements INewsView {
    NewsPresenter newsPresenter;
    RecyclerView recViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        newsPresenter = new NewsPresenter(this, new DataManager());
        loadRSS();
    }

    private void initViews() {
        recViewNews = findViewById(R.id.recViewNews);
        recViewNews.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadRSS() {
      newsPresenter.getDataForView();
    }

    @Override
    public void loadModelView(List<ModelView> listModelView) {

        recViewNews.setHasFixedSize(false);
        NewsAdapter newsAdapter = new NewsAdapter(listModelView);
        recViewNews.setAdapter(newsAdapter);
    }
}
