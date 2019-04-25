package com.example.newsagregator.presenter;

import com.example.newsagregator.presenter.model_view.ModelView;

import java.util.List;

public interface IRepoNews {

    void getData(FinishedListener finishedListener);

    interface FinishedListener {

        void setData(List<ModelView> listModelView);
    }
}
