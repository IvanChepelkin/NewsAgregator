package com.example.newsagregator.model.data.network;
import java.util.Set;

public interface NewsRemoteDataSource {

    void setSubcriber(CallBackApi callBackApi);

    interface CallBackApi {

        void onCompletedFromServer(final boolean onFinished);

        void onError(Throwable t);
    }
}