package com.example.newsagregator.model.domain;

import java.util.List;
import java.util.Set;

public interface NewsPresenterListener {

    void setData(List<NewsItem> listNewsItem);

    void setChannelsList(Set<String> channelListSet);

    void setError(Throwable exeption);
}
