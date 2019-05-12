package com.example.newsagregator.di;

import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.NewsRepositoryImpl;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.data.db.NewsDataBaseSourceImpl;
import com.example.newsagregator.model.data.network.HTTPConnections;
import com.example.newsagregator.model.data.network.NewsRemoteDataSourceImpl;
import com.example.newsagregator.model.data.shared_preferences.NewsSharedPrefDataSourceImpl;
import com.example.newsagregator.model.domain.NewsUseCaseImpl;
import com.example.newsagregator.presenter.NewsPresenter;
import com.example.newsagregator.presenter.NewsView;
import com.example.newsagregator.view.MainActivity;

public class Factory {

    private static DataBaseHelper dataBaseSourceInstance;


    public static NewsPresenter createObjectNewsPresenter(){
        return new NewsPresenter(Factory.createGetUseCaseImpl());
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
    public static NewsRemoteDataSourceImpl createObjectNewsBroadcastReceiverImpl() {

        return new NewsRemoteDataSourceImpl();
    }

    public static NewsDataBaseSourceImpl createObjectDataBaseNewsSourceImpl() {
        return new NewsDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
    }


    private static NewsSharedPrefDataSourceImpl createObjectNewsSharedPrefDataSourceImpl(){
        return new NewsSharedPrefDataSourceImpl(ApplicationContextSingleton.getContext());
    }

    private static ConverterJGONObjectInListData createObjectConverterJGONObjectInListData() {
        return new ConverterJGONObjectInListData();
    }

    private static NewsRepositoryImpl createObjectNewsRepositoryImpl() {
        return new NewsRepositoryImpl(
                Factory.createObjectNewsBroadcastReceiverImpl(),
                Factory.createObjectNewsSharedPrefDataSourceImpl());
    }

    public static NewsUseCaseImpl createGetUseCaseImpl() {
        return new NewsUseCaseImpl(Factory.createObjectNewsRepositoryImpl());
    }
}
