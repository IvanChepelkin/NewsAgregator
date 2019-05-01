package com.example.newsagregator.model.domain;

import com.example.newsagregator.model.data.NewsRepository;

import java.util.List;


public class NewsUseCaseImpl implements NewsUseCase, NewsRepository.CallBacRepo {
    NewsRepository newsRepository;
    NewsListener newsListener;

    public NewsUseCaseImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    @Override
    public void getData(NewsListener newsListener) {
        this.newsListener = newsListener;
        newsRepository.getData(this);
    }

    @Override
    public void saveChannel(String channelUrl) {

    }

    @Override
    public void setData(final List<NewsItem> listNewsItem) {
        newsListener.setData(listNewsItem);
    }
}
