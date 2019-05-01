package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsUseCase;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.List;

public class NewsPresenter implements NewsUseCase.NewsListener {
    private INewsView newsViewImpl;
    private NewsUseCase newsUseCase;

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

    public void setClickOkAddChannel(final String channelUrl) {
        newsUseCase.saveChannel(channelUrl);
    }

    @Override
    public void setData(List<NewsItem> listNewsItem) {
        newsViewImpl.showNews(listNewsItem);

    }
}