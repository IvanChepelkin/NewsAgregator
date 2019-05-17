package com.example.newsagregator.model.domain.Channel;

import java.util.List;

public interface ChannelPresenterListener {

    void setChannelsItemList(List<ChannelItem> channelItemListList);
    void ChannelsDeleteCompleted(Boolean onFinishDeleteChannels);
}
