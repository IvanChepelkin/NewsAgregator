package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.NewsEmptity;

import java.util.List;

public interface DataBaseNewsSource {

        void addNewsInDataBase(List<NewsEmptity> newsEmptityList);

        List<NewsEmptity> getNewsFromDataBase();
}
