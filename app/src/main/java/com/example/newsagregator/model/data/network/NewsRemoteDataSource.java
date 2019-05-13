package com.example.newsagregator.model.data.network;

public interface NewsRemoteDataSource {

    void setSubcriber(CallBackApi callBackApi);

    interface CallBackApi {

        void onCompletedFromServer(final boolean onFinished);

        void onError(Throwable t);
    }
}