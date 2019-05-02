package com.example.newsagregator.model.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.newsagregator.di.ApplicationContextSingleton;

import java.util.Set;

public class NewsRemoteDataSourceImpl extends BroadcastReceiver implements NewsRemoteDataSource {
    Context context;
    private String KEY_SERVICE = "channels";
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

    @Override
    public void loadDataFromServer(Set<String> channelListSet) {
        this.context = ApplicationContextSingleton.getContext();

//        final ArrayList<String> channellistArrayList = new ArrayList<>(channelListSet);
//        NewsRemoteDataSourceImpl newsBroadcastReceiver = new NewsRemoteDataSourceImpl();
//
//        // регистрируем BroadcastReceiver
//        IntentFilter intentFilter = new IntentFilter(NewsIntentService.ACTION_NEWSINTENTSERVICE);
//        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
//        context.registerReceiver(newsBroadcastReceiver, intentFilter);
//
//        Intent intent = new Intent(context, NewsIntentService.class);
//        intent.putStringArrayListExtra(KEY_SERVICE, channellistArrayList);
//        context.startService(intent);
    }
}
