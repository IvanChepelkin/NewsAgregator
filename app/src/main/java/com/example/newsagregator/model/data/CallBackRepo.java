package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsItem;

import java.util.List;
import java.util.Set;

public interface CallBackRepo {
    void setData(List<NewsItem> listNewsItem);

    void setChannelList(Set<String> channelListSet);

    void setError(Throwable exeption);
}
