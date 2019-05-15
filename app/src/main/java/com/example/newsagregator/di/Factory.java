package com.example.newsagregator.di;

import com.example.newsagregator.model.data.ChannelRepositoryImpl;
import com.example.newsagregator.model.data.ConverterJONObjectInListData;
import com.example.newsagregator.model.data.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.NewsRepositoryImpl;
import com.example.newsagregator.model.data.db.ChannelDataBaseSourceImpl;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.data.db.NewsDataBaseSourceImpl;
import com.example.newsagregator.model.data.network.HTTPConnections;
import com.example.newsagregator.model.data.network.OnFinishBroadcastReceiver;
import com.example.newsagregator.model.data.shared_preferences.NewsSharedPrefDataSourceImpl;
import com.example.newsagregator.model.domain.UseCaseImpl;
import com.example.newsagregator.presenter.NewsPresenter;

public class Factory {

    private static DataBaseHelper dataBaseSourceInstance;
    private static UseCaseImpl useCaseInstance;


    public static NewsPresenter createObjectNewsPresenter() {
        return new NewsPresenter(
                Factory.createObjectControlLogic(),
                Factory.createObjectControlLogic(),
                Factory.createObjectControlLogic());
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

    public static ChannelDataBaseSourceImpl createObjectChannelDataBaseSourceImpl() {
        return new ChannelDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
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
                Factory.createObjectNewsBroadcastReceiverImpl(),
                Factory.createObjectNewsSharedPrefDataSourceImpl());
    }

    private static ChannelRepositoryImpl createObjectChannelRepositoryImpl() {
        return new ChannelRepositoryImpl(
                Factory.createObjectNewsBroadcastReceiverImpl(),
                createObjectChannelDataBaseSourceImpl());
    }

    public static UseCaseImpl createObjectControlLogic() {
        if (useCaseInstance == null) {
            useCaseInstance = new UseCaseImpl(
                    Factory.createObjectNewsRepositoryImpl(),
                    Factory.createObjectChannelRepositoryImpl());
        }
        return useCaseInstance;

    }
}
