package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsEmptity;

import java.util.List;

public class NewsPresenter implements IRepoNews.FinishedListener {
    private INewsView newsViewImpl;
    private IRepoNews iRepoNews;

    public NewsPresenter(INewsView newsViewImpl, IRepoNews iRepoNews) {
        this.newsViewImpl = newsViewImpl;
        this.iRepoNews = iRepoNews;
    }

    public void getDataForView() {
       iRepoNews.getData(this);
    }

    @Override
    public void setData(List<NewsEmptity> listNewsEmptity) {
        newsViewImpl.showNews(listNewsEmptity);

    }
}