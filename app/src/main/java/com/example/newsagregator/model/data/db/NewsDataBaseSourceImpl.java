package com.example.newsagregator.model.data.db;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NewsDataBaseSourceImpl implements NewsDataBaseSource {
    private DataBaseHelper dataBaseHelper;

    public NewsDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public Single<List<NewsItem>> loadNewsFromDataBase() {
        return Single.fromCallable(() -> dataBaseHelper.getNewsFromDataBase())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void saveNewsInDataBase(final List<NewsItem> newsItemList) {
        dataBaseHelper.addNewsInDataBase(newsItemList);

    }
}