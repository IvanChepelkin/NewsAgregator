package com.example.newsagregator.model.domain.CallbacksInterfaces;

import com.example.newsagregator.model.domain.Channel.ChannelItem;

import java.util.List;

public interface ChannelPresenterListener {

    void setChannelsItemList(List<ChannelItem> channelItemListList);
}
