package com.example.newsagregator.model.data.newsRepo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.model.data.db.NewsDataBaseSource;

import com.example.newsagregator.model.data.network.news_remote.NewsRemoteDataSource;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;
import java.util.Objects;

import io.reactivex.Single;

public class NewsRepositoryImpl implements NewsRemoteDataSource, NewsRepository {

    private NewsRemoteDataSource newsRemoteDataSource;
    private NewsDataBaseSource newsDateBaseSource;
    private Context context;

    public NewsRepositoryImpl(NewsRemoteDataSource newsRemoteDataSource, NewsDataBaseSource newsDataBaseSource) {

        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsDateBaseSource = newsDataBaseSource;
        this.context = ApplicationContextSingleton.getContext();
    }

    @Override

    public Single<List<NewsItem>> getNews(List<String> channelList) {

        if (isOnline()) {
          return  newsRemoteDataSource.getNews(channelList)
                  .doOnSuccess(newsItemList -> newsDateBaseSource.saveNewsInDataBase(newsItemList))
                  .flatMap(newsItemList -> newsDateBaseSource.loadNewsFromDataBase());
        } else {
            return newsDateBaseSource.loadNewsFromDataBase();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}

