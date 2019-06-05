package com.example.newsagregator.model.data.network.news_remote;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.channelRepo.channel_converter.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.network.LoadDataHttp;
import com.example.newsagregator.model.data.network.news_remote.NewsRemoteDataSource;
import com.example.newsagregator.model.data.newsRepo.news_converter.ConverterJONObjectInListData;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class NewsRemoteDataSourceImpl implements NewsRemoteDataSource {

    private LoadDataHttp loadDataHttp;
    private ConverterJONObjectInListData converterJONObjectInListData;
    private final String API_KEY = "&api_key=ktqj6tz7a5tpcb3u5yqie1rxtvqyk0vb1t75fys9";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    public NewsRemoteDataSourceImpl(LoadDataHttp loadDataHttp) {
        this.loadDataHttp = loadDataHttp;
        this.converterJONObjectInListData = Factory.createObjectConverterJGONObjectInListData();
    }

    @Override
    public Single<List<NewsItem>> getNews(final List<String> channelList) {
        return Single.fromCallable(() -> {
            List<NewsItem> listNewsItem = new ArrayList<>();
            for (String url : channelList) {
                JSONObject newsObject = loadDataHttp.getHttpData(RSS_to_GSON + url + API_KEY);
                listNewsItem.addAll(converterJONObjectInListData.setListModelView(newsObject));
            }
            return listNewsItem;
        }).subscribeOn(Schedulers.io());
    }
}
