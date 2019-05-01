package com.example.newsagregator.model.data.network;

import org.json.JSONObject;

import java.util.Set;

public interface NewsRemoteDataSource {

    void setSubcriber(CallBackApi callBackApi);
    void loadDataFromServer(Set<String> channelList);

    interface CallBackApi {

        void onCompletedFromServer(final boolean onFinished);

        void onError(Throwable t);
    }
}