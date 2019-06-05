package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;

public interface NewsView {
    void showNews(List<NewsItem> listNewsItem);

    void showAlertDialogAddChannel();

    void showAlertDialogDeleteChannel(String[] channelsArray);

    void showErrorToast();

    void showErrorInvalidAddress();

    void showErrorNotCahnnelToast();

    void showErrorIsChannelToast();

    void showProgress();

    void hideProgress();

    void showMainConent(String guid);

    void sendGuide(String guid);

    void clearList();

}
