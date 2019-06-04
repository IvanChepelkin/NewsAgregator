package com.example.newsagregator.model.data.db;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import java.util.List;
import java.util.concurrent.Callable;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ChannelsDataBaseSourceImpl implements ChannelsDataBaseSource {

    private DataBaseHelper dataBaseHelper;

    public ChannelsDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public Single<List<ChannelItem>> loadChannelsFromDataBase() {
        return Single.fromCallable(new Callable<List<ChannelItem>>() {
            @Override
            public List<ChannelItem> call() throws Exception {
                return dataBaseHelper.getChannelsFromDataBase();
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteChannels(final List<String> channelsToDeleteList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dataBaseHelper.deleteChannels(channelsToDeleteList);
            }
        }).subscribeOn(Schedulers.io());
    }
}
