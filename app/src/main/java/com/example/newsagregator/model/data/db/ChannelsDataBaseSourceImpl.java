package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ChannelsDataBaseSourceImpl implements ChannelsDataBaseSource {

    private DataBaseHelper dataBaseHelper;

    public ChannelsDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public Single<List<ChannelItem>> loadChannelsFromDataBase() {
        return Single.fromCallable(() -> dataBaseHelper.getChannelsFromDataBase())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteChannels(final List<String> channelsToDeleteList) {
        return Completable.fromAction(() -> dataBaseHelper.deleteChannels(channelsToDeleteList))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void saveChannel(ChannelItem newChannelItem) {
        dataBaseHelper.addChannelInDataBase(newChannelItem);
    }
}
