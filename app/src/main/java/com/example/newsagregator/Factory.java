package com.example.newsagregator;

import com.example.newsagregator.model.DataManager;
import com.example.newsagregator.model.db.DataBaseSource;
import com.example.newsagregator.model.network.DataRemoteSource;
import com.example.newsagregator.model.network.HTTPConnections;

public class Factory {

    private static DataRemoteSource dataRemoteSourceInstance;
    private static DataBaseSource dataBaseSourceInstance;


    public static HTTPConnections createObjectHTTPConnections() {
        return new HTTPConnections();
    }

    public static DataRemoteSource createObjectDataRemoteSource() {
        if (dataRemoteSourceInstance == null) {
            dataRemoteSourceInstance = new DataRemoteSource(Factory.createObjectHTTPConnections());
        }
        return dataRemoteSourceInstance;
    }

    public static DataBaseSource createObjectDataBaseSource() {
        if (dataBaseSourceInstance == null) {
            dataBaseSourceInstance = new DataBaseSource(ApplicationContextSingleton.getContext());
        }
        return dataBaseSourceInstance;
    }

    public static DataManager createObjectDataManager() {
        return new DataManager(Factory.createObjectDataRemoteSource(), Factory.createObjectDataBaseSource());
    }

}
