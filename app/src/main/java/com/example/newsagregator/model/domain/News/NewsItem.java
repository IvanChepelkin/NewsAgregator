package com.example.newsagregator.model.domain.News;

public class NewsItem {
    private String datePublication;
    private String channelName;
    private String title;
    private String guide;
    private String content;

    public NewsItem(String channelName, String datePublication, String title, String guide, String content) {
        this.channelName = channelName;
        this.datePublication = datePublication;
        this.title = title;
        this.guide = guide;
        this.content = content;
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
}
