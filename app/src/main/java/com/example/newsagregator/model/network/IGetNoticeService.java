package com.example.newsagregator.model.network;

import com.example.newsagregator.model.network.models.NewsRssObject;

public interface IGetNoticeService {
    void setSubcriber(CallBackApi callBackApi);

    interface CallBackApi {

        void onCompleted(NewsRssObject rssObject);

        void onError(Throwable t);
    }

}
