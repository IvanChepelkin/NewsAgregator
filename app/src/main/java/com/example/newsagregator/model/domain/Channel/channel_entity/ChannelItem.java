package com.example.newsagregator.model.domain.Channel.channel_entity;

public class ChannelItem {
    private String channelName;
    private String channelUrl;

    public ChannelItem(String channelName, String channelUrl) {
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
