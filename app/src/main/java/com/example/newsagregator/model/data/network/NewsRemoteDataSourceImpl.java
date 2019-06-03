package com.example.newsagregator.model.data.network;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.channelRepo.channel_converter.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.newsRepo.news_converter.ConverterJONObjectInListData;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class NewsRemoteDataSourceImpl implements NewsRemoteDataSource {

    private LoadDataHttp loadDataHttp;
    private ConverterJONObjectInListData converterJONObjectInListData;
    private ConverterJSONObjectInChannel converterJSONObjectInChannel;

    public NewsRemoteDataSourceImpl(LoadDataHttp loadDataHttp) {
        this.loadDataHttp = loadDataHttp;
        this.converterJONObjectInListData = Factory.createObjectConverterJGONObjectInListData();
        this.converterJSONObjectInChannel = Factory.createObjectConverterJSONObjectInChannel();
    }

    @Override
    public Single<List<NewsItem>> getNews(final List<String> channelList) {
        return Single.fromCallable(new Callable<List<NewsItem>>() {
            @Override
            public List<NewsItem> call() throws Exception {
                List<NewsItem> listNewsItem = new ArrayList<>();
                for (String url : channelList) {
                    JSONObject newsObject = loadDataHttp.getHttpData(url);
                    listNewsItem.addAll(converterJONObjectInListData.setListModelView(newsObject));
                }

                return listNewsItem;
            }
        }).subscribeOn(Schedulers.io());
    }
}
