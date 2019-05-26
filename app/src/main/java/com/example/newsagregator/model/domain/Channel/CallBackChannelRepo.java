package com.example.newsagregator.model.domain.Channel;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

public interface CallBackChannelRepo {

    void setChannelList(List<ChannelItem> channelListSet);

    void ChannelsDeleteCompleted(Boolean onFinishDeleteChannels);
}
