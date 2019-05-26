package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Single;

public interface ChannelLoadDataBaseSource {

    void setSubcriber(ChannelsLoadCallBackDb channelsLoadCallBackDb);

    Single<List<ChannelItem>> loadChannelsFromDataBase();

    interface ChannelsLoadCallBackDb {

        void ChannelsLoadCompletedFromDateBase(List<ChannelItem> channelItemListFromDateBase);

    }
}
