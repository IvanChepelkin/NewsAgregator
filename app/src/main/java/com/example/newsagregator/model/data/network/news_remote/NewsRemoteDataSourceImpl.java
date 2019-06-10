package com.example.newsagregator.model.data.network.news_remote;

import com.example.newsagregator.model.data.network.HttpConnect;
import com.example.newsagregator.model.data.newsRepo.news_converter.ConverterJONObjectInListData;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class NewsRemoteDataSourceImpl implements NewsRemoteDataSource {

    private HttpConnect httpConnect;
    private ConverterJONObjectInListData converterJONObjectInListNews;
    private final String API_KEY = "&api_key=ktqj6tz7a5tpcb3u5yqie1rxtvqyk0vb1t75fys9";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    public NewsRemoteDataSourceImpl(HttpConnect httpConnect, ConverterJONObjectInListData converterJONObjectInListNews) {
        this.httpConnect = httpConnect;
        this.converterJONObjectInListNews = converterJONObjectInListNews;
    }

    @Override
    public Single<List<NewsItem>> getNews(final List<String> channelList) {
        return Single.fromCallable(() -> {
            List<NewsItem> listNewsItem = new ArrayList<>();
            for (String url : channelList) {
                JSONObject newsObject = httpConnect.getJsonObjectNews(RSS_to_GSON + url + API_KEY);
                listNewsItem.addAll(converterJONObjectInListNews.convertGsonInNewsEntity(newsObject));
            }
            return listNewsItem;
        }).subscribeOn(Schedulers.io());
    }
}
