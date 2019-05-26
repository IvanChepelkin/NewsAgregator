package com.example.newsagregator.model.data.db;
        import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
        import java.util.List;
        import java.util.concurrent.Callable;

        import io.reactivex.Scheduler;
        import io.reactivex.Single;
        import io.reactivex.schedulers.Schedulers;

public class ChanneloadDataBaseSourceImpl implements ChannelLoadDataBaseSource {

    private DataBaseHelper dataBaseHelper;
    private ChannelsLoadCallBackDb channelsLoadCallBackDb;

    public ChanneloadDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }


    @Override
    public void setSubcriber(ChannelsLoadCallBackDb channelsLoadCallBackDb) {

    }

    @Override
    public Single<List<ChannelItem>> loadChannelsFromDataBase() {
        return Single.fromCallable(new Callable<List<ChannelItem>>() {
            @Override
            public List<ChannelItem> call() {
                return dataBaseHelper.getChannelsFromDataBase();
            }
        }).subscribeOn(Schedulers.io());

    }


}
