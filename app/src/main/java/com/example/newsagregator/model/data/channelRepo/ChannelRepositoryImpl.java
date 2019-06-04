package com.example.newsagregator.model.data.channelRepo;

import com.example.newsagregator.model.data.db.ChannelsDataBaseSource;
import com.example.newsagregator.model.data.network.channel_remote.ChannelRemoteDataSource;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ChannelRepositoryImpl implements ChannelRepository {

    private ChannelsDataBaseSource channelsDataBaseSource;
    private ChannelRemoteDataSource channelRemoteDataSource;

    public ChannelRepositoryImpl(ChannelsDataBaseSource channelsDataBaseSource, ChannelRemoteDataSource channelRemoteDataSource) {
        this.channelsDataBaseSource = channelsDataBaseSource;
        this.channelRemoteDataSource = channelRemoteDataSource;
    }

    @Override
    public Completable deleteChannels(List<String> channelsToDeleteList) {
        return channelsDataBaseSource.deleteChannels(channelsToDeleteList);
    }

    @Override
    public Single<ChannelItem> saveChannels(String channelToSave) {
        return channelRemoteDataSource.saveChannels(channelToSave)
                .doOnSuccess(ChannelItem -> channelsDataBaseSource.saveChannel(ChannelItem));
    }

    @Override
    public Single<List<ChannelItem>> getChannels() {
        return channelsDataBaseSource.loadChannelsFromDataBase();
    }
}
