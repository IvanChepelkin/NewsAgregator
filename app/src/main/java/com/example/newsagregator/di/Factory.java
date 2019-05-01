package com.example.newsagregator.di;

import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.NewsRepositoryImpl;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.data.db.NewsDataBaseSourceImpl;
import com.example.newsagregator.model.data.network.NewsRemoteDataSourceImpl;
import com.example.newsagregator.model.data.network.HTTPConnections;
import com.example.newsagregator.model.data.shared_preferences.NewsSharedPrefDataSourceImpl;
import com.example.newsagregator.model.domain.NewsUseCaseImpl;

public class Factory {

    private static NewsRemoteDataSourceImpl dataRemoteSourceInstance;
    private static DataBaseHelper dataBaseSourceInstance;
    private static NewsDataBaseSourceImpl dataBaseNewsSourceInstance;


    public static HTTPConnections createObjectHTTPConnections() {
        return new HTTPConnections();
    }

    public static NewsRemoteDataSourceImpl createObjectDataRemoteSource() {
        if (dataRemoteSourceInstance == null) {
            dataRemoteSourceInstance = new NewsRemoteDataSourceImpl(Factory.createObjectHTTPConnections());
        }
        return dataRemoteSourceInstance;
    }

    public static DataBaseHelper createObjectDataBaseHelper() {
        if (dataBaseSourceInstance == null) {
            dataBaseSourceInstance = new DataBaseHelper(ApplicationContextSingleton.getContext());
        }
        return dataBaseSourceInstance;
    }

    public static NewsDataBaseSourceImpl createObjectDataBaseNewsSourceImpl() {
        if (dataBaseNewsSourceInstance == null) {
            dataBaseNewsSourceInstance = new NewsDataBaseSourceImpl(Factory.createObjectDataBaseHelper());
        }
        return dataBaseNewsSourceInstance;
    }

    private static NewsSharedPrefDataSourceImpl createObjectNewsSharedPrefDataSourceImpl(){
        return new NewsSharedPrefDataSourceImpl(ApplicationContextSingleton.getContext());
    }

    private static ConverterJGONObjectInListData createObjectConverterJGONObjectInListData() {
        return new ConverterJGONObjectInListData();
    }

    private static NewsRepositoryImpl createObjectNewsRepositoryImpl() {
        return new NewsRepositoryImpl(
                Factory.createObjectDataRemoteSource(),
                Factory.createObjectDataBaseNewsSourceImpl(),
                Factory.createObjectNewsSharedPrefDataSourceImpl(),
                createObjectConverterJGONObjectInListData());
    }

    public static NewsUseCaseImpl createGetUseCaseImpl() {
        return new NewsUseCaseImpl(Factory.createObjectNewsRepositoryImpl());
    }
}
