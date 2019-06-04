package com.example.newsagregator.model.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;

public class OnFinishBroadcastReceiver extends BroadcastReceiver implements NewsRemoteDataSource {

    CallBackApi callBackApi;

    public OnFinishBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean onFinish = Objects.requireNonNull(intent.getExtras()).getBoolean(NewsIntentService.EXTRA_KEY_SUCCESS);
       if (onFinish){
           callBackApi.onCompletedFromServer(onFinish);
       }
       else {
           Bundle extras = intent.getExtras().getBundle(NewsIntentService.EXTRA_KEY_SUCCESS);
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
