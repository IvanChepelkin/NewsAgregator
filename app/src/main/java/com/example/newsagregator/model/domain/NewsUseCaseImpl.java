package com.example.newsagregator.model.domain;

import com.example.newsagregator.model.data.CallBackRepo;
import com.example.newsagregator.model.data.NewsRepository;
import java.util.List;
import java.util.Set;


public class NewsUseCaseImpl implements NewsUseCase, CallBackRepo {

    private NewsRepository newsRepository;
    private NewsPresenterListener newsListener;

    public NewsUseCaseImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    @Override
    public void getData(NewsPresenterListener newsListener) {
        this.newsListener = newsListener;
        newsRepository.getData(this);
    }

    @Override
    public void saveChannel(final String channelUrl) {
        newsRepository.saveChannel(channelUrl);
    }

    @Override
    public void channelsList() {
        newsRepository.returnChannelsList();
    }

    @Override
    public void deleteChannels(final List<String> channelsToDeleteList) {
        newsRepository.deleteChannels(channelsToDeleteList);
    }

    @Override
    public void setData(final List<NewsItem> listNewsItem) {
        newsListener.setData(listNewsItem);
    }

    @Override
    public void setChannelList(Set<String> channelListSet) {
        newsListener.setChannelsList(channelListSet);
    }

    @Override
    public void setError(Throwable exeption) {
        newsListener.setError(exeption);
    }
}
