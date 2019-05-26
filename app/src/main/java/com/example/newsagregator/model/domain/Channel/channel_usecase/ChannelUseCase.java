package com.example.newsagregator.model.domain.Channel.channel_usecase;

import java.util.List;

public interface ChannelUseCase {
    void deleteChannel(final List<String> channelsToDeleteList);
    void getChannels();
}
