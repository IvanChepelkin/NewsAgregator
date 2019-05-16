package com.example.newsagregator.model.domain.Channel;

import java.util.List;

public interface ChannelUseCase {
    void saveChannel(final String channelUrl);
    void deleteChannel(final List<String> channelsToDeleteList);
}
