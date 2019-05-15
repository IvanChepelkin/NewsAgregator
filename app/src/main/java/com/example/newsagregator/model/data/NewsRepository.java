package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.Channel.CallBackChannelRepo;
import com.example.newsagregator.model.domain.Channel.ChannelItem;
import com.example.newsagregator.model.domain.News.CallBackNewsRepo;

import java.util.List;

public interface NewsRepository {

    void subscribeNewsRepository(CallBackNewsRepo callBackNewsRepo);

    void getData(List<String> channelList);

    void returnChannelsList();


}