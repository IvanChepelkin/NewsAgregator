package com.example.newsagregator.model.domain;

import java.util.List;
import java.util.Set;

public interface NewsUseCase {

    void getData(NewsListener newsListener);
    void saveChannel(final String channelUrl);
    void channelsList();

    interface NewsListener {

        void setData(List<NewsItem> listNewsItem);
        void setChannelsList(Set<String> channelListSet);
    }
}
