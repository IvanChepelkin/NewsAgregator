package com.example.newsagregator.model.domain.News.news_usecase;

import com.example.newsagregator.model.data.newsRepo.NewsRepository;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import java.util.List;

import io.reactivex.Single;

public class NewsUseCaseImpl implements NewsUseCase {
    private NewsRepository newsRepository;

    public NewsUseCaseImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }


    @Override
    public Single<List<NewsItem>> getNews(List<String> channelList) {
        return newsRepository.getNews(channelList);
    }
}
