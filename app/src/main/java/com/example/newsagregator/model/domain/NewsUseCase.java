package com.example.newsagregator.model.domain;

import java.util.List;
import java.util.Set;

public interface NewsUseCase {

    void getData(NewsPresenterListener newsPresenterListener);
    void saveChannel(final String channelUrl);
    void channelsList();
    void deleteChannels(final List<String> channelsToDeleteList);
}
