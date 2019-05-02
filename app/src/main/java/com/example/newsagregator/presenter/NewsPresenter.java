package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsUseCase;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class NewsPresenter implements NewsUseCase.NewsListener {
    private INewsView newsViewImpl;
    private NewsUseCase newsUseCase;
    List<NewsItem> listNewsItem;
    ArrayList<String> List;

    public NewsPresenter(INewsView newsViewImpl, NewsUseCase newsUseCase) {
        this.newsViewImpl = newsViewImpl;
        this.newsUseCase = newsUseCase;
    }

    public void updateNews() {
        newsUseCase.getData(this);
    }

    public void setClickAddChannel() {
        newsViewImpl.showAlertDialogAddChannel();
    }

    public void setClickDeleteChannel() {
        newsUseCase.channelsList();
    }

    public void setClickOkAddChannel(final String channelUrl) {
        newsUseCase.saveChannel(channelUrl);
    }

    @Override
    public void setData(List<NewsItem> listNewsItem) {
        this.listNewsItem = listNewsItem;
        Collections.reverse(listNewsItem);
        newsViewImpl.showNews(listNewsItem);

    }

    @Override
    public void setChannelsList(Set<String> channelListSet) {
        String[] channelsArray = channelListSet.toArray(new String[0]);
        newsViewImpl.showAlertDialogDeleteChannel(channelsArray);
    }
}