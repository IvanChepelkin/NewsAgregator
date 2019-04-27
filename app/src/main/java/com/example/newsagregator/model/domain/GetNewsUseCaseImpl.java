package com.example.newsagregator.model.domain;

import com.example.newsagregator.model.data.RepoNews;
import java.util.List;


public class GetNewsUseCaseImpl implements GetNewsUseCase,RepoNews.CallBacRepo {
    RepoNews repoNews;
    PresenterListener presenterListener;

    public GetNewsUseCaseImpl(RepoNews repoNews) {
        this.repoNews = repoNews;
    }


    @Override
    public void getData(PresenterListener presenterListener) {
        this.presenterListener = presenterListener;
        repoNews.getData(this);
    }

    @Override
    public void setData(List<NewsEmptity> listNewsEmptity) {
        presenterListener.setData(listNewsEmptity);
    }
}
