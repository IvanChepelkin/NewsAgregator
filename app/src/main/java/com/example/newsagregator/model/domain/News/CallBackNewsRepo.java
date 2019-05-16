package com.example.newsagregator.model.domain.News;

import java.util.List;

public interface CallBackNewsRepo {

    void setNewsItemList(List<NewsItem> listNewsItem);

    void setError(Throwable exeption);
}
