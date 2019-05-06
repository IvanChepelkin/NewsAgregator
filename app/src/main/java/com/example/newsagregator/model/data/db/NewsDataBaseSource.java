package com.example.newsagregator.model.data.db;
import com.example.newsagregator.model.domain.NewsItem;


import java.util.List;

public interface NewsDataBaseSource {

    void loadNewsFromDataBase();

    void setSubcriber(CallBackDb callBackDb);


    interface CallBackDb {

        void onCompletedFromDateBase(List<NewsItem> newsItemListFromDateBase);

    }
}
