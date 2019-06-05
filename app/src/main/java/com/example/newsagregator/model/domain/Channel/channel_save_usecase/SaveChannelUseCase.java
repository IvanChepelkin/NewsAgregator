package com.example.newsagregator.model.domain.Channel.channel_save_usecase;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;


import io.reactivex.Single;

public interface SaveChannelUseCase {
    Single <ChannelItem> saveChannel(final String channelToSave);
}
