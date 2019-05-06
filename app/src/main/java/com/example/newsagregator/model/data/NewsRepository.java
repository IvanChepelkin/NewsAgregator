package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsItem;

import java.util.List;
import java.util.Set;

public interface NewsRepository {
    void getData(CallBacRepo callBackApi);

    void saveChannel(final String channelUrl);

    void returnChannelsList();

    void deleteChannels(final List<String> ChannelsToDeleteList);

    interface CallBacRepo {

        void setData(List<NewsItem> listNewsItem);

        void setChannelList(Set<String> channelListSet);

    }
}