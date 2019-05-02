package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsItem;

import java.util.ArrayList;
import java.util.List;

public interface INewsView {
        void showNews(List<NewsItem> listNewsItem);
        void showAlertDialogAddChannel();
        void showAlertDialogDeleteChannel(ArrayList<String> channelsList);
}
