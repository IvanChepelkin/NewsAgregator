package com.example.newsagregator.model.domain.Channel;

import java.util.List;

public interface ChannelUseCase {
    void addChannel(final String channelUrl);
    void deleteChannel(final List<String> channelsToDeleteList);
    void getChannels();
}
