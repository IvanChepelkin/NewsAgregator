package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsUseCase;
import com.example.newsagregator.model.domain.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class NewsPresenter implements NewsUseCase.NewsListener {
    private NewsView newsView;
    private NewsUseCase newsUseCase;
    private List<NewsItem> listNewsItem;
    private String[] channelsArray;

    public NewsPresenter(NewsView newsView, NewsUseCase newsUseCase) {
        this.newsView = newsView;
        this.newsUseCase = newsUseCase;
    }

    public void updateNews() {
        newsUseCase.getData(this);
        newsView.showProgress();
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

        newsView.showError(exeption.toString());
    }
}