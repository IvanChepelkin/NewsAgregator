package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsItem;
import java.util.List;

public interface NewsRepository {
    void getData(CallBacRepo callBackApi);
    void saveChannel(final String channelUrl);

    interface CallBacRepo {

        void setData(List<NewsItem> listNewsItem);

    }
}