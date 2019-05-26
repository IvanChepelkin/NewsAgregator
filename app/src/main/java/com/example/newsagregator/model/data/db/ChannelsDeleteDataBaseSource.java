package com.example.newsagregator.model.data.db;

import java.util.List;

public interface ChannelsDeleteDataBaseSource {

    void setSubcriber(ChannelsDeleteCallBackDb channelsDeleteCallBackDb);

    void deleteChannelsFromDataBase(final List<String> channelsToDeleteList);

    interface ChannelsDeleteCallBackDb {

        void ChannelsDeleteCompletedFromDateBase(Boolean onFinishDeleteChannels);

    }
}
