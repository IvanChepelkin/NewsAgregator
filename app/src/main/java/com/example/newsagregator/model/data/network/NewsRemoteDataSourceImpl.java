package com.example.newsagregator.model.data.network;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.channelRepo.channel_converter.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.newsRepo.news_converter.ConverterJONObjectInListData;

import java.util.List;

public class NewsRemoteDataSourceImpl implements NewsRemoteDataSource {
    LoadDataHttp loadDataHttp;
    private ConverterJONObjectInListData converterJONObjectInListData;
    private ConverterJSONObjectInChannel converterJSONObjectInChannel;

    public NewsRemoteDataSourceImpl(LoadDataHttp loadDataHttp) {
        this.loadDataHttp = loadDataHttp;
        this.converterJONObjectInListData = Factory.createObjectConverterJGONObjectInListData();
        this.converterJSONObjectInChannel = Factory.createObjectConverterJSONObjectInChannel();
    }



    @Override
    public void getNews(final List<String> channelLis) {
        return null;
    }
}
