package com.example.newsagregator.di;

import com.example.newsagregator.model.data.channelRepo.ChannelRepositoryImpl;
import com.example.newsagregator.model.data.newsRepo.ConverterJONObjectInListData;
import com.example.newsagregator.model.data.channelRepo.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.newsRepo.NewsRepositoryImpl;
import com.example.newsagregator.model.data.db.ChanneloadDataBaseSourceImpl;
import com.example.newsagregator.model.data.db.ChannelsDeleteDataBaseSourceImpl;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.data.db.NewsDataBaseSourceImpl;
import com.example.newsagregator.model.data.network.HTTPConnections;
import com.example.newsagregator.model.data.network.OnFinishBroadcastReceiver;
import com.example.newsagregator.model.data.shared_preferences.NewsSharedPrefDataSourceImpl;
import com.example.newsagregator.model.domain.Channel.ChannelUseCaseImpl;
import com.example.newsagregator.model.domain.News.NewsUseCaseImpl;
import com.example.newsagregator.presenter.NewsPresenter;

public class Factory {

    private static DataBaseHelper dataBaseSourceInstance;
    private static NewsUseCaseImpl newsUseCaseInstance;
    private static ChannelUseCaseImpl channelUseCasenstance;


    public static NewsPresenter createObjectNewsPresenter() {
        return new NewsPresenter(Factory.createObjectNewsUseCaseImpl(),
                Factory.createObjectChannelUseCaseImplImpl(),
                Factory.createObjectNewsUseCaseImpl(),
                Factory.createObjectChannelUseCaseImplImpl());
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

    public static ChanneloadDataBaseSourceImpl createObjectChannelSloadDataBaseSourceImpl() {
        return new ChanneloadDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }

    public static ChannelsDeleteDataBaseSourceImpl createObjectChannelsDeleteDataBaseSourceImpl() {
        return new ChannelsDeleteDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }


    private static NewsSharedPrefDataSourceImpl createObjectNewsSharedPrefDataSourceImpl() {
        return new NewsSharedPrefDataSourceImpl(ApplicationContextSingleton.getContext());
    }

    public static ConverterJONObjectInListData createObjectConverterJGONObjectInListData() {
        return new ConverterJONObjectInListData();
    }

    public static ConverterJSONObjectInChannel createObjectConverterJSONObjectInChannel() {
        return new ConverterJSONObjectInChannel();
    }

    private static NewsRepositoryImpl createObjectNewsRepositoryImpl() {
        return new NewsRepositoryImpl(
                Factory.createObjectNewsBroadcastReceiverImpl());
    }

    private static ChannelRepositoryImpl createObjectChannelRepositoryImpl() {
        return new ChannelRepositoryImpl(Factory.createObjectNewsBroadcastReceiverImpl(),
                Factory.createObjectChannelSloadDataBaseSourceImpl(),
                Factory.createObjectChannelsDeleteDataBaseSourceImpl());
    }

    public static NewsUseCaseImpl createObjectNewsUseCaseImpl() {
        if (newsUseCaseInstance == null) {
            newsUseCaseInstance = new NewsUseCaseImpl(Factory.createObjectNewsRepositoryImpl());
        }
        return newsUseCaseInstance;

    }

    public static ChannelUseCaseImpl createObjectChannelUseCaseImplImpl() {
        if (channelUseCasenstance == null) {
            channelUseCasenstance = new ChannelUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
        }
        return channelUseCasenstance;

    }
}
