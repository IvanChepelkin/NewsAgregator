package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.Channel.ChannelItem;

import java.util.List;

public interface ChannelLoadDataBaseSource {

    void setSubcriber(ChannelsLoadCallBackDb channelsLoadCallBackDb);

    void loadChannelsFromDataBase();

    interface ChannelsLoadCallBackDb {

        void ChannelsLoadCompletedFromDateBase(List<ChannelItem> channelItemListFromDateBase);

    }
}
