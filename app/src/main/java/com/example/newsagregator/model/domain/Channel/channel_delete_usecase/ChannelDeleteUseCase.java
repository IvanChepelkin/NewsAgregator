package com.example.newsagregator.model.domain.Channel.channel_delete_usecase;

import java.util.List;

import io.reactivex.Completable;

public interface ChannelDeleteUseCase {
    Completable deleteChannels(final List<String> channelsToDeleteList);
}
