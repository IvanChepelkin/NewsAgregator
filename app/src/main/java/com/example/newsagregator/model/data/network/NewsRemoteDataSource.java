package com.example.newsagregator.model.data.network;

import java.util.List;

public interface NewsRemoteDataSource {

    void getNews(List<String> channelLis);

}