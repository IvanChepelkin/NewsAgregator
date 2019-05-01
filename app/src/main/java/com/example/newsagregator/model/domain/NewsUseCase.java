package com.example.newsagregator.model.domain;

import java.util.List;

public interface NewsUseCase {

    void getData(NewsListener newsListener);

    interface NewsListener {

        void setData(List<NewsItem> listNewsItem);
    }
}
