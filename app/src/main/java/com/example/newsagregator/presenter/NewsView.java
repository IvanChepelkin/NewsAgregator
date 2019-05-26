package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;

public interface NewsView {
    void showNews(List<NewsItem> listNewsItem);

    void showAlertDialogAddChannel();

    void showAlertDialogDeleteChannel(String[] channelsArray);

    void showErrorToast();

    void showNotCahnnelToast();

    void showProgress();

    void hideProgress();

    void showMainConent(String guid);

}
