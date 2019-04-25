package com.example.newsagregator.presenter;

import com.example.newsagregator.presenter.model_view.ModelView;

import java.util.List;

public class NewsPresenter implements IRepoNews.FinishedListener {
    private INewsView inewsView;
    private IRepoNews iRepoNews;

    public NewsPresenter(INewsView inewsView, IRepoNews iRepoNews) {
        this.inewsView = inewsView;
        this.iRepoNews = iRepoNews;
    }

    public void getDataForView() {
       iRepoNews.getData(this);
    }

    @Override
    public void setData(List<ModelView> listModelView) {
        inewsView.loadModelView(listModelView);

    }
}