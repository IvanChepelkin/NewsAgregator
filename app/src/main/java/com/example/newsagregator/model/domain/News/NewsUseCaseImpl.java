package com.example.newsagregator.model.domain.News;

import com.example.newsagregator.model.data.newsRepo.NewsRepository;

import java.util.List;

public class NewsUseCaseImpl implements SubscribeUseCaseNews,NewsUseCase,CallBackNewsRepo {
    private NewsRepository newsRepository;
    private NewsPresenterListener newsPresenterListener;



    public NewsUseCaseImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        newsRepository.subscribeNewsRepository(this);
    }

    @Override
    public void getNews(List<String> channelList) {
        newsRepository.getNews(channelList);
    }

    @Override
    public void setNewsItemList(List<NewsItem> listNewsItem) {
        newsPresenterListener.setNewsItemList(listNewsItem);

    }

    @Override
    public void setError(Throwable exeption) {
        newsPresenterListener.setError(exeption);
    }

    @Override
    public void subscribePresenterNews(NewsPresenterListener newsPresenterListener) {
        this.newsPresenterListener = newsPresenterListener;
    }
}
