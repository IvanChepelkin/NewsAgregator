package com.example.newsagregator.model.data.network;

import org.json.JSONObject;

public interface LoadDataHttp {

    void getHttpData(CallBackHttp callBackHttp, String urlChannel);

    interface CallBackHttp {

        void onSuccess(JSONObject newsItemList);

        void onError(Throwable ex);
    }
}
