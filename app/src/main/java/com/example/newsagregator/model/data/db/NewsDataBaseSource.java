package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;

import io.reactivex.Single;

public interface NewsDataBaseSource {

    Single<List<NewsItem>> loadNewsFromDataBase();

    void saveNewsInDataBase(List<NewsItem> newsItemList);

}