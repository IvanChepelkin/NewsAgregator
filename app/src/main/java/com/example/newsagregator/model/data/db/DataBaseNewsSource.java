package com.example.newsagregator.model.data.db;
import com.example.newsagregator.model.domain.NewsEmptity;


import java.util.List;

public interface DataBaseNewsSource {

    void loadNewsFromDataBase();

    void setSubcriber(CallBackDb callBackDb);

    interface CallBackDb {

        void onCompletedFromDateBase(List<NewsEmptity> newsEmptityListFromDateBase);

    }
}
