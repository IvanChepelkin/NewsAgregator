package com.example.newsagregator.model.data.db;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import java.util.ArrayList;
import java.util.List;

public class ChanneloadDataBaseSourceImpl extends AsyncTask<Void, Void, List<ChannelItem>> implements ChannelLoadDataBaseSource {

    private DataBaseHelper dataBaseHelper;
    private ChannelsLoadCallBackDb channelsLoadCallBackDb;

    public ChanneloadDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    protected List<ChannelItem> doInBackground(Void... voids) {
        dataBaseHelper = Factory.createObjectDataBaseHelper();
        List<ChannelItem> channelItemList = new ArrayList<>();
        channelItemList = dataBaseHelper.getChannelsFromDataBase();
        return channelItemList;

    }

    @Override
    protected void onPostExecute(List<ChannelItem> channelItemList) {
        channelsLoadCallBackDb.ChannelsLoadCompletedFromDateBase(channelItemList);
    }

    @Override
    public void loadChannelsFromDataBase() {
        execute();
    }


    @Override
    public void setSubcriber(ChannelsLoadCallBackDb channelsLoadCallBackDb) {
        this.channelsLoadCallBackDb = channelsLoadCallBackDb;

    }

}
