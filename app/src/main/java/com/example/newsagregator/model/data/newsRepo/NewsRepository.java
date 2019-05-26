package com.example.newsagregator.model.data.newsRepo;

import com.example.newsagregator.model.domain.News.CallBackNewsRepo;

import java.util.List;

public interface NewsRepository {

    void subscribeNewsRepository(CallBackNewsRepo callBackNewsRepo);

    void getNews(List<String> channelList);

}