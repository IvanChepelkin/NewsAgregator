package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.CallbacksInterfaces.ChannelPresenterListener;
import com.example.newsagregator.model.domain.CallbacksInterfaces.NewsPresenterListener;
import com.example.newsagregator.model.domain.Channel.ChannelItem;
import com.example.newsagregator.model.domain.Channel.ChannelUseCase;
import com.example.newsagregator.model.domain.News.NewsUseCase;
import com.example.newsagregator.model.domain.News.NewsItem;
import com.example.newsagregator.model.domain.SubscribeUseCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsPresenter implements ChannelPresenterListener, NewsPresenterListener {
    private NewsView newsView;
    private NewsUseCase newsUseCase;
    private ChannelUseCase channelUseCase;
    private SubscribeUseCase subscribeUseCase;
    private List<NewsItem> listNewsItem;
    private List<ChannelItem> channelItemListList;
    private String[] channelsArray;

    public NewsPresenter(NewsUseCase newsUseCase,
                         ChannelUseCase channelUseCase,
                         SubscribeUseCase subscribeUseCase) {

        this.newsUseCase = newsUseCase;
        this.channelUseCase = channelUseCase;
        this.subscribeUseCase = subscribeUseCase;
        subscribeUseCase.subscribePresenterChannels(this);
        subscribeUseCase.subscribePresenterNews(this);
    }

    public void onAttach(NewsView newsView) {
        this.newsView = newsView;
        if (newsView != null) {
            updateNews();
        }
        newsUseCase.getData();
    }

    public void updateNews() {
        newsView.showProgress();
    }

    public void setClickAddChannel() {
        newsView.showAlertDialogAddChannel();
    }

    public void setClickDeleteChannel() {
        newsView.showAlertDialogDeleteChannel(channelsArray);
    }

    public void setClickOkAddChannels(final String channelUrl) {

        channelUseCase.addChannel(channelUrl);
    }

    public void setClickOkDeleteChannels(final boolean[] positionCheckboxArray) {
        List<String> channelsToDeleteList = new ArrayList<>();

        for (int i = 0; i < positionCheckboxArray.length; i++) {
            if (positionCheckboxArray[i]) {
                channelsToDeleteList.add(channelItemListList.get(i).getChannelUrl());
            }
        }
        channelUseCase.deleteChannel(channelsToDeleteList);
    }

    public void setClickItemNews(int position) {
        String guid = listNewsItem.get(position).getGuide();
        newsView.showMainConent(guid);
    }

    @Override
    public void setNewsItemList(final List<NewsItem> listNewsItem) {
        this.listNewsItem = listNewsItem;
        Collections.reverse(listNewsItem);
        newsView.hideProgress();
        newsView.showNews(listNewsItem);
    }

//    @Override
//    public void setChannelsItemList(Set<String> channelListSet) {
//        channelsArray = channelListSet.toArray(new String[0]);
//
//    }

    @Override
    public void setError(Throwable exeption) {
        newsView.showError("Неправильный адрес запроса");
    }

    @Override
    public void setChannelsItemList(List<ChannelItem> channelItemListList) {
        this.channelItemListList = channelItemListList;
        List<String> channelList = new ArrayList<>();

        for (int i = 0; i < channelItemListList.size(); i++) {
            channelList.add(channelItemListList.get(i).getChannelName());
        }
        channelsArray = channelList.toArray(new String[0]);
       // newsView.showAlertDialogDeleteChannel(channelsArray);

    }
}