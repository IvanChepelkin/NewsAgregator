package com.example.newsagregator.model.domain.News;

public class NewsItem {
    private String date;
    private String channelName;
    private String title;
    private String guide;
    private String content;

    public NewsItem(String date, String channelName, String title, String guide, String content) {
        this.date = date;
        this.channelName = channelName;
        this.title = title;
        this.guide = guide;
        this.content = content;
    }

    public String getDate() {
        return date;
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
}
