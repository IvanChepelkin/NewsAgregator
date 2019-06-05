package com.example.newsagregator.model.data.network.channel_remote;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import io.reactivex.Single;

public interface ChannelRemoteDataSource {
    Single<ChannelItem> saveChannels(final String channelToSaveList);
}
