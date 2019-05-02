package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsUseCase;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NewsPresenter implements NewsUseCase.NewsListener {
    private INewsView newsViewImpl;
    private NewsUseCase newsUseCase;
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
        newsViewImpl.showAlertDialogDeleteChannel(List);
    }

    public void setClickOkAddChannel(final String channelUrl) {
        newsUseCase.saveChannel(channelUrl);
    }

    @Override
    public void setData(List<NewsItem> listNewsItem) {
        Collections.reverse(listNewsItem);
        newsViewImpl.showNews(listNewsItem);

    }
}