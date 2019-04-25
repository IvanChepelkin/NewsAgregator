package com.example.newsagregator.model.network;

import org.json.JSONObject;

public interface IGetNewsFromRemote {

    void setSubcriber(CallBackApi callBackApi);

    interface CallBackApi {

        void onCompleted(JSONObject jsonObjectNews);

        void onError(Throwable t);
    }
}