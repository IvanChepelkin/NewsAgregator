package com.example.newsagregator.model.domain.News.news_entity;

public class NewsItem {
    private String datePublication;
    private String channelName;
    private String title;
    private String guide;
    private String content;
    private String url;

    public NewsItem(String channelName, String datePublication, String title, String guide, String content, String url) {
        this.channelName = channelName;
        this.datePublication = datePublication;
        this.title = title;
        this.guide = guide;
        this.content = content;
        this.url = url;

    }

    public String getDatePublication() {
        return datePublication;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getTitle() {
        return title;
    }

    public String getGuide() {
        return guide;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
