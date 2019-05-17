package com.example.newsagregator.model.data.channelRepo;

import com.example.newsagregator.model.domain.Channel.CallBackChannelRepo;

import java.util.List;

public interface ChannelRepository {
    void subscribeChannelRepository(CallBackChannelRepo callBackChannelRepo);

    void saveChannel(final String channelUrl);

    void deleteChannels(final List<String> channelsToDeleteList);

    void getChannels();
}
