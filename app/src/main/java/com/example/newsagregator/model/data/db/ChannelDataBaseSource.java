package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.Channel.ChannelItem;
import com.example.newsagregator.model.domain.News.NewsItem;

import java.util.List;

public interface ChannelDataBaseSource {

    void loadChannelsFromDataBase();

    void setSubcriber(ChannelsCallBackDb channelsCallBackDb);


    interface ChannelsCallBackDb {

        void ChannelsonCompletedFromDateBase(List<ChannelItem> channelItemListFromDateBase);

    }
}
