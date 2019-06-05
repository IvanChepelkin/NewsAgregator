package com.example.newsagregator.model.domain.News.news_usecase;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;

import io.reactivex.Single;

public interface GetNewsUseCase {
    Single<List<NewsItem>> getNews(List<String> channelList);
}
