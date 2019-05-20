package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.Channel.ChannelPresenterListener;
import com.example.newsagregator.model.domain.News.NewsPresenterListener;
import com.example.newsagregator.model.domain.Channel.ChannelItem;
import com.example.newsagregator.model.domain.Channel.ChannelUseCase;
import com.example.newsagregator.model.domain.Channel.SubscribeChannelUseCase;
import com.example.newsagregator.model.domain.News.NewsUseCase;
import com.example.newsagregator.model.domain.News.NewsItem;
import com.example.newsagregator.model.domain.News.SubscribeUseCaseNews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsPresenter implements ChannelPresenterListener, NewsPresenterListener {
    private NewsView newsView;
    private NewsUseCase newsUseCase;
    private ChannelUseCase channelUseCase;
    private List<NewsItem> listNewsItem;
    private List<ChannelItem> channelItemList;
    private String[] channelsArray;
    private String channeSavelUrl;

    public NewsPresenter(NewsUseCase newsUseCase,
                         ChannelUseCase channelUseCase,
                         SubscribeUseCaseNews subscribeUseCaseNews,
                         SubscribeChannelUseCase subscribeChannelUseCase) {

        this.newsUseCase = newsUseCase;
        this.channelUseCase = channelUseCase;
        subscribeChannelUseCase.subscribePresenterChannels(this);
        subscribeUseCaseNews.subscribePresenterNews(this);
    }

    public void onAttach(NewsView newsView) {
        this.newsView = newsView;
        if (newsView != null) {
            updateNews();
        }
    }

    public void updateNews() {
        newsView.showProgress();
        channelUseCase.getChannels();
    }

    public void setClickAddChannel() {
        newsView.showAlertDialogAddChannel();
    }

    public void setClickDeleteChannel() {
        newsView.showAlertDialogDeleteChannel(channelsArray);
    }

    public void setClickOkAddChannels(final String channeSavelUrl) {

        this.channeSavelUrl = channeSavelUrl;
        channelUseCase.getChannels();
    }

    public void setClickOkDeleteChannels(final boolean[] positionCheckboxArray) {
        List<String> channelsToDeleteList = new ArrayList<>();

        for (int i = 0; i < positionCheckboxArray.length; i++) {
            if (positionCheckboxArray[i]) {
                channelsToDeleteList.add(channelItemList.get(i).getChannelUrl());
            }
        }
        channelUseCase.deleteChannel(channelsToDeleteList);
    }

    public void setClickItemNews(int position) {
        String guid = listNewsItem.get(position).getGuide();
        newsView.showMainConent(guid);
    }

    private void setChannelsArray(List<ChannelItem> channelItemListList) {
        List<String> channelList = new ArrayList<>();

        for (int i = 0; i < channelItemListList.size(); i++) {
            channelList.add(channelItemListList.get(i).getChannelName());
        }
        channelsArray = channelList.toArray(new String[0]);

    }

    private void loadNews(final List<ChannelItem> channelItemListList) {

        List<String> channelList = new ArrayList<>();

        for (int i = 0; i < channelItemListList.size(); i++) {
            channelList.add(channelItemListList.get(i).getChannelUrl());
        }

        if (channeSavelUrl != null) {
            channelList.add(channeSavelUrl);
            newsUseCase.getNews(channelList);

        } else {
            newsUseCase.getNews(channelList);
        }

    }

    @Override
    public void setNewsItemList(final List<NewsItem> listNewsItem) {
        this.listNewsItem = listNewsItem;
        Collections.reverse(listNewsItem);
        newsView.hideProgress();
        newsView.showNews(listNewsItem);
    }

    @Override
    public void setError(Throwable exeption) {
        newsView.showError("Неправильный адрес запроса");
    }

    @Override
    public void setChannelsItemList(List<ChannelItem> channelItemList) {

        if (channelItemList.size() == 0) {
            newsView.showError("У вас нет добавленных каналов");
            newsView.hideProgress();
        } else {
            this.channelItemList = channelItemList;
            setChannelsArray(channelItemList);
            loadNews(channelItemList);
        }

        this.channelItemList = channelItemList;
        setChannelsArray(channelItemList);
        loadNews(channelItemList);


    }

    @Override
    public void ChannelsDeleteCompleted(Boolean onFinishDeleteChannels) {
        if (onFinishDeleteChannels) {
            channelUseCase.getChannels();
        }
    }
}