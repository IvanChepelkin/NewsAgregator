package com.example.newsagregator.model.domain.CallbacksInterfaces;

import java.nio.channels.Channel;
import java.util.List;

public interface ChannelPresenterListener {

    void setChannelsList(List<Channel> channelListSet);
}
