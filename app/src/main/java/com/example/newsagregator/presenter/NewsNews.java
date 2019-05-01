package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsUseCase;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.List;

public class NewsNews implements NewsUseCase.NewsListener {
    private INewsView newsViewImpl;
    private NewsUseCase newsUseCase;

    public NewsNews(INewsView newsViewImpl, NewsUseCase newsUseCase) {
        this.newsViewImpl = newsViewImpl;
        this.newsUseCase = newsUseCase;
    }

    public void updateNews() {
       newsUseCase.getData(this);
    }

    @Override
    public void setData(List<NewsItem> listNewsItem) {
        newsViewImpl.showNews(listNewsItem);

    }
}