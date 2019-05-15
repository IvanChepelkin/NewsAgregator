package com.example.newsagregator.model.data.db;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.Channel.ChannelItem;
import java.util.ArrayList;
import java.util.List;

public class ChannelDataBaseSourceImpl extends AsyncTask<Void, Void, List<ChannelItem>> implements ChannelDataBaseSource {

    private DataBaseHelper dataBaseHelper;
    private ChannelsCallBackDb channelsCallBackDb;

    public ChannelDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
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
        channelsCallBackDb.ChannelsonCompletedFromDateBase(channelItemList);
    }

    @Override
    public void loadChannelsFromDataBase() {
        execute();
    }

    @Override
    public void setSubcriber(ChannelsCallBackDb channelsCallBackDb) {
        this.channelsCallBackDb = channelsCallBackDb;

    }

}
