package com.example.newsagregator.model.data.network;

import org.json.JSONObject;

public interface RemoteNewsDataSource {

    void setSubcriber(CallBackApi callBackApi);
    void loadDataFromServer();

    interface CallBackApi {

        void onCompletedFromServer(JSONObject jsonObjectNews);

        void onError(Throwable t);
    }
}