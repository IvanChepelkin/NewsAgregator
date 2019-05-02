package com.example.newsagregator.model.domain;

import com.example.newsagregator.model.data.NewsRepository;

import java.util.List;
import java.util.Set;


public class NewsUseCaseImpl implements NewsUseCase, NewsRepository.CallBacRepo {
    private NewsRepository newsRepository;
    private NewsListener newsListener;

    public NewsUseCaseImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    @Override
    public void getData(NewsListener newsListener) {
        this.newsListener = newsListener;
        newsRepository.getData(this);
    }

    @Override
    public void saveChannel(final String channelUrl) {
        newsRepository.saveChannel(channelUrl);
    }

    @Override
    public void channelsList() {
        newsRepository.channelsList();
    }

    @Override
    public void setData(final List<NewsItem> listNewsItem) {
        newsListener.setData(listNewsItem);
    }

    @Override
    public void setChannelList(Set<String> channelListSet) {
        newsListener.setChannelsList(channelListSet);
    }
}
