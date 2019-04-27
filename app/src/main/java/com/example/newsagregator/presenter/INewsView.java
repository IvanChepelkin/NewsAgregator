package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.NewsEmptity;

import java.util.List;

public interface INewsView {
        void showNews(List<NewsEmptity> listNewsEmptity);
}
