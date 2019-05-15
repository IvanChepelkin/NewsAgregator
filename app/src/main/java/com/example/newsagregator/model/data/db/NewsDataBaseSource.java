package com.example.newsagregator.model.data.db;
import com.example.newsagregator.model.domain.News.NewsItem;


import java.util.List;

public interface NewsDataBaseSource {

    void loadNewsFromDataBase();

    void setSubcriber(NewsCallBackDb newsCallBackDb);


    interface NewsCallBackDb {

        void onCompletedFromDateBase(List<NewsItem> newsItemListFromDateBase);

    }
}