package com.example.newsagregator.model.data.channelRepo;

import com.example.newsagregator.model.data.db.ChannelsDataBaseSource;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ChannelRepositoryImpl implements ChannelRepository {

    private ChannelsDataBaseSource channelsDataBaseSource;

    public ChannelRepositoryImpl(ChannelsDataBaseSource channelsDataBaseSource) {
        this.channelsDataBaseSource = channelsDataBaseSource;
    }

    @Override
    public Completable deleteChannels(List<String> channelsToDeleteList) {
        return channelsDataBaseSource.deleteChannels(channelsToDeleteList);
    }

    @Override
    public Single<List<ChannelItem>> getChannels() {
        return channelsDataBaseSource.loadChannelsFromDataBase();
    }
}
