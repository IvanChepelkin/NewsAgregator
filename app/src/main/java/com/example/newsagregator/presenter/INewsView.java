package com.example.newsagregator.presenter;
import com.example.newsagregator.presenter.model_view.ModelView;

import java.util.List;

public interface INewsView {
    void loadModelView(List<ModelView> listModelView);
}
