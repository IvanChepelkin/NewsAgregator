package com.example.newsagregator.model.data.network;

import org.json.JSONObject;

import java.util.Set;

public interface NewsRemoteDataSource {

    void setSubcriber(CallBackApi callBackApi);
    void loadDataFromServer(Set<String> channelList);

    interface CallBackApi {

        void onCompletedFromServer(JSONObject jsonObjectNews);

        void onError(Throwable t);
    }
}