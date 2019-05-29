package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NewsDataBaseSourceImpl implements NewsDataBaseSource {
    private DataBaseHelper dataBaseHelper;

    public NewsDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public Single<List<NewsItem>> loadNewsFromDataBase() {
        return Single.fromCallable(new Callable<List<NewsItem>>() {
            @Override
            public List<NewsItem> call() throws Exception {
                return dataBaseHelper.getNewsFromDataBase();
            }
        }).subscribeOn(Schedulers.io());
    }
}