package com.example.newsagregator.model.data.newsRepo;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;

import io.reactivex.Single;

public interface NewsRepository {

    Single<List<NewsItem>> getNews(List<String> channelLis);

}