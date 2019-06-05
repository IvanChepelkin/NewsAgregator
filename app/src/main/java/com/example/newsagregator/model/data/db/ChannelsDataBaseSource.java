package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ChannelsDataBaseSource {

    Single<List<ChannelItem>> loadChannelsFromDataBase();
    Completable deleteChannels(final List<String> channelsToDeleteList);
    void saveChannel(final ChannelItem newChannelItem);

}
