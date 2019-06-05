package com.example.newsagregator.model.domain.Channel.channel_save_usecase;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;


import io.reactivex.Single;

public interface ChannelSaveUseCase {
    Single <ChannelItem> saveChannels(final String channelToSave);
}
