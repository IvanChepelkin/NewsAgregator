package com.example.newsagregator.di;

import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.DataManager;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.data.db.DataBaseNewsSourceImpl;
import com.example.newsagregator.model.data.network.RemoteNewsDataSourceIml;
import com.example.newsagregator.model.data.network.HTTPConnections;
import com.example.newsagregator.model.domain.GetNewsUseCaseImpl;

public class Factory {

    private static RemoteNewsDataSourceIml dataRemoteSourceInstance;
    private static DataBaseHelper dataBaseSourceInstance;
    private static DataBaseNewsSourceImpl dataBaseNewsSourceInstance;


    public static HTTPConnections createObjectHTTPConnections() {
        return new HTTPConnections();
    }

    public static RemoteNewsDataSourceIml createObjectDataRemoteSource() {
        if (dataRemoteSourceInstance == null) {
            dataRemoteSourceInstance = new RemoteNewsDataSourceIml(Factory.createObjectHTTPConnections());
        }
        return dataRemoteSourceInstance;
    }

    public static DataBaseHelper createObjectDataBaseHelper() {
        if (dataBaseSourceInstance == null) {
            dataBaseSourceInstance = new DataBaseHelper(ApplicationContextSingleton.getContext());
        }
        return dataBaseSourceInstance;
    }

    public static DataBaseNewsSourceImpl createObjectDataBaseNewsSourceImpl() {
        if (dataBaseNewsSourceInstance == null) {
            dataBaseNewsSourceInstance = new DataBaseNewsSourceImpl(Factory.createObjectDataBaseHelper());
        }
        return dataBaseNewsSourceInstance;
    }

    public static ConverterJGONObjectInListData createObjectConverterJGONObjectInListData() {
        return new ConverterJGONObjectInListData();
    }

    public static DataManager createObjectDataManager() {
        return new DataManager(
                Factory.createObjectDataRemoteSource(), Factory.createObjectDataBaseNewsSourceImpl(),
                createObjectConverterJGONObjectInListData());
    }

    public static GetNewsUseCaseImpl createGetUseCaseImpl() {
        return new GetNewsUseCaseImpl(Factory.createObjectDataManager());
    }

}
