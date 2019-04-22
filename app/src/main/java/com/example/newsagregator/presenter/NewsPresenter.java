package com.example.newsagregator.presenter;

import com.example.newsagregator.presenter.model_view.ModelView;

import java.util.List;

public class NewsPresenter implements INewsData.FinishedListener {
    private INewsView newsView;
    private INewsData newsData;

    public NewsPresenter(INewsView newsView, INewsData newsData) {
        this.newsView = newsView;
        this.newsData = newsData;
    }

    public void getDataForView() {
        newsData.getData(this);
    }

    @Override
    public void setData(List<ModelView> listModelView) {
        newsView.loadModelView(listModelView);

    }
}