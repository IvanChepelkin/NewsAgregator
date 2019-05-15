package com.example.newsagregator.model.domain.News;

import java.util.List;

public interface NewsUseCase {
    void getData();
    void channelsList();
    void deleteChannels(final List<String> channelsToDeleteList);
}