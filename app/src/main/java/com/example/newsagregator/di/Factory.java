package com.example.newsagregator.di;

import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.DataManager;
import com.example.newsagregator.model.data.db.DataBaseNewsSourceImpl;
import com.example.newsagregator.model.data.network.RemoteNewsDataSourceIml;
import com.example.newsagregator.model.data.network.HTTPConnections;

public class Factory {

    private static RemoteNewsDataSourceIml dataRemoteSourceInstance;
    private static DataBaseNewsSourceImpl dataBaseSourceInstance;


    public static HTTPConnections createObjectHTTPConnections() {
        return new HTTPConnections();
    }

    public static RemoteNewsDataSourceIml createObjectDataRemoteSource() {
        if (dataRemoteSourceInstance == null) {
            dataRemoteSourceInstance = new RemoteNewsDataSourceIml(Factory.createObjectHTTPConnections());
        }
        return dataRemoteSourceInstance;
    }

    public static DataBaseNewsSourceImpl createObjectDataBaseNewsSource() {
        if (dataBaseSourceInstance == null) {
            dataBaseSourceInstance = new DataBaseNewsSourceImpl(ApplicationContextSingleton.getContext());
        }
        return dataBaseSourceInstance;
    }

    public static ConverterJGONObjectInListData createObjectConverterJGONObjectInListData()
    {
        return new ConverterJGONObjectInListData();
    }

    public static DataManager createObjectDataManager() {
        return new DataManager(
                Factory.createObjectDataRemoteSource(),Factory.createObjectDataBaseNewsSource(),
                createObjectConverterJGONObjectInListData());
    }

}
