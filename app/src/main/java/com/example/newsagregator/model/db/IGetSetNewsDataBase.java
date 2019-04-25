package com.example.newsagregator.model.db;

import com.example.newsagregator.presenter.model_view.ModelView;
import java.util.List;

public interface IGetSetNewsDataBase {

    void addNewsInDataBase(List<ModelView> modelViewList);

    List<ModelView> getNewsFromDataBase();

}
