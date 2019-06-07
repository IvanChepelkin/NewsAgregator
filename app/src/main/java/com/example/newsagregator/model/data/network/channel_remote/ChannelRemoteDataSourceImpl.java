package com.example.newsagregator.model.data.network.channel_remote;

import com.example.newsagregator.model.data.channelRepo.channel_converter.ConverterJSONObjectInChannel;
import com.example.newsagregator.model.data.network.HttpConnect;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChannelRemoteDataSourceImpl implements ChannelRemoteDataSource {

    private HttpConnect httpConnect;
    private ConverterJSONObjectInChannel converterJSONObjectInChannel;
    private final String API_KEY = "&api_key=ktqj6tz7a5tpcb3u5yqie1rxtvqyk0vb1t75fys9";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    public ChannelRemoteDataSourceImpl(HttpConnect httpConnect, ConverterJSONObjectInChannel converterJSONObjectInChannel) {
        this.httpConnect = httpConnect;
        this.converterJSONObjectInChannel = converterJSONObjectInChannel;
    }

    @Override
    public Single<ChannelItem> getChannel(String channelToSave) {
        return Single.fromCallable(() -> {
            JSONObject channelObject = httpConnect.getJsonObjectNews(RSS_to_GSON + channelToSave + API_KEY);
            ChannelItem newChannelItem = converterJSONObjectInChannel.convertGsonObjectInChannelEntity(channelObject);
            return newChannelItem;
        }).subscribeOn(Schedulers.io());
    }
}
