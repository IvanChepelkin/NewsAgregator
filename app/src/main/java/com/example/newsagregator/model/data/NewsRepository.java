package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsItem;
import java.util.List;

public interface NewsRepository {
    void getData(CallBacRepo callBackApi);

    interface CallBacRepo {

        void setData(List<NewsItem> listNewsItem);

    }
}