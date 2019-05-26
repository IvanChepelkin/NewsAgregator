package com.example.newsagregator.model.data.channelRepo;

import com.example.newsagregator.model.domain.Channel.CallBackChannelRepo;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Single;

public interface ChannelRepository {
    void subscribeChannelRepository(CallBackChannelRepo callBackChannelRepo);

    void saveChannel(final String channelUrl);

    void deleteChannels(final List<String> channelsToDeleteList);

    Single<List<ChannelItem>> getChannels();
}
