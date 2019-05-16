package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.Channel.ChannelItem;

import java.util.List;

public interface ChannelsDeleteDataBaseSource {

    void setSubcriber(ChannelsDeleteCallBackDb channelsDeleteCallBackDb);

    void deleteChannelsFromDataBase(final List<String> channelsToDeleteList);

    interface ChannelsDeleteCallBackDb {

        void ChannelsDeleteCompletedFromDateBase(Boolean onFinishDeleteChannels);

    }
}
