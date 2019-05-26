package com.example.newsagregator.model.data.shared_preferences;

import java.util.List;
import java.util.Set;

public interface ChannelsSharedPrefDataSource {

    void saveChannelInList(final String channelUrl);

    Set<String> getChannelsUrlList();

    void deleteChannelsList(final List<String> channelsToDeleteList);
}
