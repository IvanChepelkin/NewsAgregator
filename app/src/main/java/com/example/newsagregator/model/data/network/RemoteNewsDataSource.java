package com.example.newsagregator.model.data.network;

import org.json.JSONObject;

public interface RemoteNewsDataSource {

    void setSubcriber(CallBackApi callBackApi);
    void loadData();

    interface CallBackApi {

        void onCompleted(JSONObject jsonObjectNews);

        void onError(Throwable t);
    }
}