package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.GetNewsUseCase;
import com.example.newsagregator.model.domain.NewsEmptity;

import java.util.List;

public class NewsPresenter implements GetNewsUseCase.PresenterListener {
    private INewsView newsViewImpl;
    private GetNewsUseCase getNewsUseCase;

    public NewsPresenter(INewsView newsViewImpl, GetNewsUseCase getNewsUseCase) {
        this.newsViewImpl = newsViewImpl;
        this.getNewsUseCase = getNewsUseCase;
    }

    public void getDataForView() {
       getNewsUseCase.getData(this);
    }

    @Override
    public void setData(List<NewsEmptity> listNewsEmptity) {
        newsViewImpl.showNews(listNewsEmptity);

    }
}