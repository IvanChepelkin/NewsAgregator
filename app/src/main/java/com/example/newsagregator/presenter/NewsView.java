package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.News.NewsItem;

import java.util.List;

public interface NewsView {
    void showNews(List<NewsItem> listNewsItem);

    void showAlertDialogAddChannel();

    void showAlertDialogDeleteChannel(String[] channelsArray);

    void showError(String error);

    void showProgress();

    void hideProgress();

    void showMainConent(String guid);

    void showAlertDialogFiltrChannels();


}
