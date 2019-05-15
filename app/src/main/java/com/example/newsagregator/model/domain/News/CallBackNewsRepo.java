package com.example.newsagregator.model.domain.News;

import com.example.newsagregator.model.domain.News.NewsItem;

import java.util.List;
import java.util.Set;

public interface CallBackNewsRepo {

    void setData(List<NewsItem> listNewsItem);

    void setChannelList(Set<String> channelListSet);

    void setError(Throwable exeption);
}
