package com.example.newsagregator.di;

import com.example.newsagregator.model.data.channelRepo.ChannelRepositoryImpl;
import com.example.newsagregator.model.data.network.channel_remote.ChannelRemoteDataSourceImpl;
import com.example.newsagregator.model.data.network.news_remote.NewsRemoteDataSourceImpl;
import com.example.newsagregator.model.data.newsRepo.news_converter.ConverterJONObjectInListData;
import com.example.newsagregator.model.data.channelRepo.channel_converter.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.newsRepo.NewsRepositoryImpl;
import com.example.newsagregator.model.data.db.ChannelsDataBaseSourceImpl;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.data.db.NewsDataBaseSourceImpl;
import com.example.newsagregator.model.data.network.HTTPConnections;
import com.example.newsagregator.model.data.shared_preferences.ChannelsSharedPrefDataSourceImpl;
import com.example.newsagregator.model.domain.Channel.channel_delete_usecase.ChannelDeleteUseCaseImpl;
import com.example.newsagregator.model.domain.Channel.channel_save_usecase.ChannelSaveUseCaseImpl;
import com.example.newsagregator.model.domain.Channel.channel_usecase.ChannelUseCaseImpl;
import com.example.newsagregator.model.domain.News.news_usecase.NewsUseCaseImpl;
import com.example.newsagregator.presenter.NewsPresenter;

public class Factory {

    private static DataBaseHelper dataBaseSourceInstance;

    private static NewsUseCaseImpl newsUseCaseInstance;

    public static NewsPresenter createObjectNewsPresenter() {
        return new NewsPresenter(Factory.createObjectNewsUseCaseImpl(),
                Factory.createObjectChannelUseCaseImplImpl(),
                Factory.createObjectChannelDeleteUseCaseImpl(),
                Factory.createObjectChannelSaveUseCaseImp());
    }

    public static HTTPConnections createObjectHTTPConnections() {
        return new HTTPConnections();
    }

    private static NewsRemoteDataSourceImpl createObjectNewsRemoteDataSourceImpl() {
        return new NewsRemoteDataSourceImpl(Factory.createObjectHTTPConnections()
                ,Factory.createObjectConverterJGONObjectInListData());
    }

    private static ChannelRemoteDataSourceImpl createObjectNewsChannelRemoteDataSourceImpl() {
        return new ChannelRemoteDataSourceImpl(Factory.createObjectHTTPConnections(),
                Factory.createObjectConverterJSONObjectInChannel());
    }

    public static DataBaseHelper createObjectDataBaseHelper() {
        if (dataBaseSourceInstance == null) {
            dataBaseSourceInstance = new DataBaseHelper(ApplicationContextSingleton.getContext());
        }
        return dataBaseSourceInstance;
    }

    public static NewsDataBaseSourceImpl createObjectDataBaseNewsSourceImpl() {
        return new NewsDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }

    private static ChannelsDataBaseSourceImpl createObjectChannelSloadDataBaseSourceImpl() {
        return new ChannelsDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }

    private static ChannelsSharedPrefDataSourceImpl createObjectNewsSharedPrefDataSourceImpl() {
        return new ChannelsSharedPrefDataSourceImpl(ApplicationContextSingleton.getContext());
    }

    public static ConverterJONObjectInListData createObjectConverterJGONObjectInListData() {
        return new ConverterJONObjectInListData();
    }

    public static ConverterJSONObjectInChannel createObjectConverterJSONObjectInChannel() {
        return new ConverterJSONObjectInChannel();
    }

    private static NewsRepositoryImpl createObjectNewsRepositoryImpl() {
        return new NewsRepositoryImpl(

                Factory.createObjectNewsRemoteDataSourceImpl(),
                Factory.createObjectDataBaseNewsSourceImpl());

    }

    private static ChannelRepositoryImpl createObjectChannelRepositoryImpl() {
        return new ChannelRepositoryImpl(
                Factory.createObjectChannelSloadDataBaseSourceImpl(),
                Factory.createObjectNewsChannelRemoteDataSourceImpl());
    }

    public static NewsUseCaseImpl createObjectNewsUseCaseImpl() {
        if (newsUseCaseInstance == null) {
            newsUseCaseInstance = new NewsUseCaseImpl(Factory.createObjectNewsRepositoryImpl());
        }
        return newsUseCaseInstance;
    }

    private static ChannelUseCaseImpl createObjectChannelUseCaseImplImpl() {
        return new ChannelUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }

    private static ChannelDeleteUseCaseImpl createObjectChannelDeleteUseCaseImpl() {
        return new ChannelDeleteUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }

    private static ChannelSaveUseCaseImpl createObjectChannelSaveUseCaseImp() {
        return new ChannelSaveUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }
}
