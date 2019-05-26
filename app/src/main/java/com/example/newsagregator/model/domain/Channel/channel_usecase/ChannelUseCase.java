package com.example.newsagregator.model.domain.Channel.channel_usecase;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Single;

public interface ChannelUseCase {
    void deleteChannel(final List<String> channelsToDeleteList);
    Single<List<ChannelItem>> getChannels();
}
