package com.example.newsagregator.model.domain.News;

public class NewsItem {
    private String title;
    private String guide;
    private String content;

    public NewsItem(String title, String guide, String content) {
        this.title = title;
        this.guide = guide;
        this.content = content;
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