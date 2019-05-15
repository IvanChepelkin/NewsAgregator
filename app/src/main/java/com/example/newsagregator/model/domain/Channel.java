package com.example.newsagregator.model.domain;

public class Channel {
    private String channelName;
    private String channelUrl;

    public Channel(String channelName, String channelUrl) {
        this.channelName = channelName;
        this.channelUrl = channelUrl;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }
}
