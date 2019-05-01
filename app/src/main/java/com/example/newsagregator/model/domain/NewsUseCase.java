package com.example.newsagregator.model.domain;

import java.util.List;

public interface NewsUseCase {

    void getData(NewsListener newsListener);
    void saveChannel(final String channelUrl);

    interface NewsListener {

        void setData(List<NewsItem> listNewsItem);
    }
}
