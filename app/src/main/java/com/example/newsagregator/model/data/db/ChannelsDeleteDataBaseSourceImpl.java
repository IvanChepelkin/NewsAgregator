package com.example.newsagregator.model.data.db;

import android.os.AsyncTask;
import com.example.newsagregator.di.Factory;

import java.util.List;

public class ChannelsDeleteDataBaseSourceImpl extends AsyncTask<List<String>, Void, Boolean> implements ChannelsDeleteDataBaseSource {

    private DataBaseHelper dataBaseHelper;
    private ChannelsDeleteCallBackDb channelsDeleteCallBackDb;

    public ChannelsDeleteDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @SafeVarargs
    @Override
    protected final Boolean doInBackground(List<String>... lists) {
        dataBaseHelper = Factory.createObjectDataBaseHelper();
        return  dataBaseHelper.deleteChannels(lists[0]);
    }

    @Override
    protected void onPostExecute(Boolean onFinishDeleteChannels) {
        channelsDeleteCallBackDb.ChannelsDeleteCompletedFromDateBase(onFinishDeleteChannels);
    }


    @Override
    public void setSubcriber(ChannelsDeleteCallBackDb channelsDeleteCallBackDb) {
        this.channelsDeleteCallBackDb = channelsDeleteCallBackDb;

    }

    @Override
    public void deleteChannelsFromDataBase(List<String> channelsToDeleteList) {
        execute(channelsToDeleteList);
    }

}