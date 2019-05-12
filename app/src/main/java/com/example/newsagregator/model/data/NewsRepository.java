package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsItem;

import java.util.List;
import java.util.Set;

public interface NewsRepository {

    void getData(CallBackRepo callBackApi);

    void saveChannel(final String channelUrl);

    void returnChannelsList();

    void deleteChannels(final List<String> ChannelsToDeleteList);

}