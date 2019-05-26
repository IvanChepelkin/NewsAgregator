package com.example.newsagregator.model.domain.News.news_usecase;

import java.util.List;

public interface NewsUseCase {
    void getNews(List<String> channelList);
}
