package com.example.newsagregator.presenter.model_view;

public class ModelView {
    private String title;
    private String guide;
    private String content;

    public ModelView(String title, String guide, String content) {
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
