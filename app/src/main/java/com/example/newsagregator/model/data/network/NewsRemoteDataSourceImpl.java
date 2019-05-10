package com.example.newsagregator.model.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NewsRemoteDataSourceImpl extends BroadcastReceiver implements NewsRemoteDataSource {
    CallBackApi callBackApi;

    public NewsRemoteDataSourceImpl() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean onFinish = intent.getBooleanExtra(NewsIntentService.EXTRA_KEY_SUCCESS, false);
       if (onFinish){
           callBackApi.onCompletedFromServer(onFinish);
       }
       else {
           Bundle extras = intent.getExtras();
           assert extras != null;
           Throwable exeption = (Throwable) extras.getSerializable(NewsIntentService.EXTRA_KEY_ERROR);
           callBackApi.onError(exeption);
       }

    }

    @Override
    public void setSubcriber(CallBackApi callBackApi) {
        this.callBackApi = callBackApi;
    }

}
