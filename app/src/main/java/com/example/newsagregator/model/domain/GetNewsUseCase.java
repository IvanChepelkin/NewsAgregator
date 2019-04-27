package com.example.newsagregator.model.domain;

import java.util.List;

public interface GetNewsUseCase {

    void getData(PresenterListener presenterListener);

    interface PresenterListener {

        void setData(List<NewsEmptity> listNewsEmptity);
    }
}
