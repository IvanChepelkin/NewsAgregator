package com.example.newsagregator.model.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.newsagregator.di.ApplicationContextSingleton;

import java.util.Set;

public class NewsRemoteDataSourceImpl extends BroadcastReceiver implements NewsRemoteDataSource {
    CallBackApi callBackApi;

    public NewsRemoteDataSourceImpl() {
    }

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

}
