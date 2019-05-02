package com.example.newsagregator.model.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.newsagregator.model.data.network.NewsIntentService;
import com.example.newsagregator.model.data.network.NewsRemoteDataSource;

import java.util.Set;

public class NewsBroadcastReceiver extends BroadcastReceiver implements NewsRemoteDataSource {
    CallBackApi callBackApi;

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean onFinish = intent.getBooleanExtra(NewsIntentService.EXTRA_KEY_OUT, false);
       if (onFinish){
           callBackApi.onCompletedFromServer(onFinish);
       }

    }

    @Override
    public void setSubcriber(CallBackApi callBackApi) {
        this.callBackApi = callBackApi;
    }

    @Override
    public void loadDataFromServer(Set<String> channelList) {

    }
}
