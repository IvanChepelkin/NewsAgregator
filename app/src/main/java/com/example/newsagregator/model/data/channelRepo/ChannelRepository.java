package com.example.newsagregator.model.data.channelRepo;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ChannelRepository {

    Completable deleteChannels(final List<String> channelsToDeleteList);

    Single<ChannelItem> saveChannels(final String channelToSave);

    Single<List<ChannelItem>> getChannels();
}
