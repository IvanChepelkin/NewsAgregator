package com.example.newsagregator.model.data.network;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;

import io.reactivex.Single;

public interface NewsRemoteDataSource {

    Single<List<NewsItem>> getNews(List<String> channelLis);

}