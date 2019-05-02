package com.example.newsagregator.model.data.network;
import java.util.Set;

public interface NewsRemoteDataSource {

    void setSubcriber(CallBackApi callBackApi);
    void loadDataFromServer(Set<String> channelList);

    interface CallBackApi {

        void onCompletedFromServer(final boolean onFinished);

        void onError(Throwable t);
    }
}