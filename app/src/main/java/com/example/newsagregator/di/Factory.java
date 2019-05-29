package com.example.newsagregator.di;

import com.example.newsagregator.model.data.channelRepo.ChannelRepositoryImpl;
import com.example.newsagregator.model.data.newsRepo.news_converter.ConverterJONObjectInListData;
import com.example.newsagregator.model.data.channelRepo.channel_converter.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.newsRepo.NewsRepositoryImpl;
import com.example.newsagregator.model.data.db.ChannelsDataBaseSourceImpl;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.data.db.NewsDataBaseSourceImpl;
import com.example.newsagregator.model.data.network.HTTPConnections;
import com.example.newsagregator.model.data.network.OnFinishBroadcastReceiver;
import com.example.newsagregator.model.data.shared_preferences.ChannelsSharedPrefDataSourceImpl;
import com.example.newsagregator.model.domain.Channel.channel_delete_usecase.ChannelDeleteUseCaseImpl;
import com.example.newsagregator.model.domain.Channel.channel_usecase.ChannelUseCaseImpl;
import com.example.newsagregator.model.domain.News.news_usecase.NewsUseCaseImpl;
import com.example.newsagregator.presenter.NewsPresenter;

public class Factory {

    private static DataBaseHelper dataBaseSourceInstance;

    public static NewsPresenter createObjectNewsPresenter() {
        return new NewsPresenter(Factory.createObjectNewsUseCaseImpl(),
                Factory.createObjectChannelUseCaseImplImpl(),
                Factory.createObjectChannelChannelDeleteUseCaseImpl(),
                Factory.createObjectNewsUseCaseImpl());
    }

    public static HTTPConnections createObjectHTTPConnections() {
        return new HTTPConnections();
    }

    public static DataBaseHelper createObjectDataBaseHelper() {
        if (dataBaseSourceInstance == null) {
            dataBaseSourceInstance = new DataBaseHelper(ApplicationContextSingleton.getContext());
        }
        return dataBaseSourceInstance;
    }

    public static OnFinishBroadcastReceiver createObjectNewsBroadcastReceiverImpl() {
        return new OnFinishBroadcastReceiver();
    }

    public static NewsDataBaseSourceImpl createObjectDataBaseNewsSourceImpl() {
        return new NewsDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }

    public static ChannelsDataBaseSourceImpl createObjectChannelSloadDataBaseSourceImpl() {
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
                Factory.createObjectNewsBroadcastReceiverImpl(),
                Factory.createObjectDataBaseNewsSourceImpl());
    }

    private static ChannelRepositoryImpl createObjectChannelRepositoryImpl() {
        return new ChannelRepositoryImpl(
                Factory.createObjectChannelSloadDataBaseSourceImpl());
    }

    private static NewsUseCaseImpl createObjectNewsUseCaseImpl() {
        return new NewsUseCaseImpl(Factory.createObjectNewsRepositoryImpl());
    }

    private static ChannelUseCaseImpl createObjectChannelUseCaseImplImpl() {
        return new ChannelUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }

    private static ChannelDeleteUseCaseImpl createObjectChannelChannelDeleteUseCaseImpl() {
        return new ChannelDeleteUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }
}
