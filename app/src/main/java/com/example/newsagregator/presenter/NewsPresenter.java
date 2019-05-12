package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsPresenterListener;
import com.example.newsagregator.model.domain.NewsUseCase;
import com.example.newsagregator.model.domain.NewsItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class NewsPresenter implements NewsPresenterListener {
    private NewsView newsView;
    private NewsUseCase newsUseCase;
    private List<NewsItem> listNewsItem;
    private String[] channelsArray;

    public NewsPresenter(NewsView newsView, NewsUseCase newsUseCase) {
        this.newsView = newsView;
        this.newsUseCase = newsUseCase;
    }

    public void updateNews() {
        newsView.showProgress();
        newsUseCase.getData(this);
    }

    public void setClickAddChannel() {
        newsView.showAlertDialogAddChannel();
    }

    public void setClickDeleteChannel() {
        newsUseCase.channelsList();
    }

    public void setClickOkAddChannels(final String channelUrl) {
        newsUseCase.saveChannel(channelUrl);
    }

    public void setClickOkDeleteChannels(final boolean[] positionCheckboxArray) {
        List<String> channelsToDeleteList = new ArrayList<>();

        for (int i = 0; i < positionCheckboxArray.length; i++) {
            if (positionCheckboxArray[i]) {
                channelsToDeleteList.add(channelsArray[i]);
            }
        }
        newsUseCase.deleteChannels(channelsToDeleteList);
    }

    public void setClickItemNews(int position) {
        String guid = listNewsItem.get(position).getGuide();
        newsView.showMainConent(guid);
    }

    @Override
    public void setData(final List<NewsItem> listNewsItem) {
        this.listNewsItem = listNewsItem;
        Collections.reverse(listNewsItem);
        newsView.hideProgress();
        newsView.showNews(listNewsItem);
    }

    @Override
    public void setChannelsList(Set<String> channelListSet) {
        channelsArray = channelListSet.toArray(new String[0]);
        newsView.showAlertDialogDeleteChannel(channelsArray);
    }

    @Override
    public void setError(Throwable exeption) {

        if (exeption instanceof IOException){
            newsView.showError("Ошибка");
        }

    }
}