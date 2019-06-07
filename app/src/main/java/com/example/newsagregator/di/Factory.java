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
import com.example.newsagregator.model.data.network.HTTPConnectImpl;
import com.example.newsagregator.model.data.shared_preferences.ChannelsSharedPrefDataSourceImpl;
import com.example.newsagregator.model.domain.Channel.channel_delete_usecase.DeleteChannelUseCaseImpl;
import com.example.newsagregator.model.domain.Channel.channel_save_usecase.SaveChannelUseCaseImpl;
import com.example.newsagregator.model.domain.Channel.channel_usecase.GetGetChannelUseCaseImpl;
import com.example.newsagregator.model.domain.News.news_usecase.GetGetNewsUseCaseImpl;
import com.example.newsagregator.presenter.NewsPresenter;

public class Factory {

    private static DataBaseHelper dataBaseSourceInstance;

    public static NewsPresenter createObjectNewsPresenter() {
        return new NewsPresenter(Factory.createObjectNewsUseCaseImpl(),
                Factory.createObjectChannelUseCaseImplImpl(),
                Factory.createObjectChannelDeleteUseCaseImpl(),
                Factory.createObjectChannelSaveUseCaseImp());
    }

    private static HTTPConnectImpl createObjectHTTPConnections() {
        return new HTTPConnectImpl();
    }

    private static NewsRemoteDataSourceImpl createObjectNewsRemoteDataSourceImpl() {
        return new NewsRemoteDataSourceImpl(Factory.createObjectHTTPConnections()
                ,Factory.createObjectConverterJGONObjectInListData());
    }

    private static ChannelRemoteDataSourceImpl createObjectNewsChannelRemoteDataSourceImpl() {
        return new ChannelRemoteDataSourceImpl(Factory.createObjectHTTPConnections(),
                Factory.createObjectConverterJSONObjectInChannel());
    }

    private static DataBaseHelper createObjectDataBaseHelper() {
        if (dataBaseSourceInstance == null) {
            dataBaseSourceInstance = new DataBaseHelper(ApplicationContextSingleton.getContext());
        }
        return dataBaseSourceInstance;
    }

    private static NewsDataBaseSourceImpl createObjectDataBaseNewsSourceImpl() {
        return new NewsDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }

    private static ChannelsDataBaseSourceImpl createObjectChannelSloadDataBaseSourceImpl() {
        return new ChannelsDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }

    private static ChannelsSharedPrefDataSourceImpl createObjectNewsSharedPrefDataSourceImpl() {
        return new ChannelsSharedPrefDataSourceImpl(ApplicationContextSingleton.getContext());
    }

    private static ConverterJONObjectInListData createObjectConverterJGONObjectInListData() {
        return new ConverterJONObjectInListData();
    }

    private static ConverterJSONObjectInChannel createObjectConverterJSONObjectInChannel() {
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

    private static GetGetNewsUseCaseImpl createObjectNewsUseCaseImpl() {
        return new GetGetNewsUseCaseImpl(Factory.createObjectNewsRepositoryImpl());
    }

    private static GetGetChannelUseCaseImpl createObjectChannelUseCaseImplImpl() {
        return new GetGetChannelUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }

    private static DeleteChannelUseCaseImpl createObjectChannelDeleteUseCaseImpl() {
        return new DeleteChannelUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }

    private static SaveChannelUseCaseImpl createObjectChannelSaveUseCaseImp() {
        return new SaveChannelUseCaseImpl(Factory.createObjectChannelRepositoryImpl());
    }
}
