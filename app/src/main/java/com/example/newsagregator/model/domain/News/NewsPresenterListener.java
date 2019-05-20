package com.example.newsagregator.model.domain.News;

import com.example.newsagregator.model.domain.News.NewsItem;

import java.util.List;

public interface NewsPresenterListener {

    void setNewsItemList(List<NewsItem> listNewsItem);

    void setError(Throwable exeption);
}