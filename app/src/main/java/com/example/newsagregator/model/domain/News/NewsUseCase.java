package com.example.newsagregator.model.domain.News;

import java.util.List;

public interface NewsUseCase {
    void getNews(List<String> channelList);
    void setError(Throwable exeption);
}
