package com.example.newsagregator.model.domain;

import com.example.newsagregator.model.domain.CallbacksInterfaces.ChannelPresenterListener;
import com.example.newsagregator.model.domain.CallbacksInterfaces.NewsPresenterListener;

public interface SubscribeUseCase {

    void subscribePresenterNews(NewsPresenterListener newsPresenterListener);
    void subscribePresenterChannels(ChannelPresenterListener channelPresenterListener);

}
