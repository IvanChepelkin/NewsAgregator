package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsEmptity;

import java.util.List;

public interface IRepoNews {

    void getData(FinishedListener finishedListener);

    interface FinishedListener {

        void setData(List<NewsEmptity> listNewsEmptity);
    }
}
