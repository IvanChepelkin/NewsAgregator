package com.example.newsagregator.model.data.shared_preferences;

import java.util.Set;

public interface NewsSharedPrefDataSource {

    void putChannelInList(final String channelUrl);

    Set<String> getChannelUrlList();

    void deleteChannel(final String channelUrl);
}
