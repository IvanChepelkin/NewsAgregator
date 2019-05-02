package com.example.newsagregator.model.data;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.model.data.db.NewsDataBaseSource;
import com.example.newsagregator.model.data.network.NewsIntentService;
import com.example.newsagregator.model.data.network.NewsRemoteDataSource;
import com.example.newsagregator.model.data.shared_preferences.NewsSharedPrefDataSource;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NewsRepositoryImpl implements NewsRemoteDataSource.CallBackApi, NewsDataBaseSource.CallBackDb, NewsRepository {

    //    private List<NewsItem> listNewsItem = new ArrayList<>();
    private NewsRemoteDataSource newsRemoteDataSource;
    private NewsDataBaseSource newsDateBaseNewsSource;
    private NewsSharedPrefDataSource newsSharedPrefDataSource;
    private NewsRepository.CallBacRepo callBackRepo;
    private ConverterJGONObjectInListData converterJGONObjectInListData;
    private Context context;
    private String KEY_SERVICE = "channels";


    public NewsRepositoryImpl(NewsRemoteDataSource newsRemoteDataSource,
                              NewsDataBaseSource newsDateBaseNewsSource,
                              NewsSharedPrefDataSource newsSharedPrefDataSource,
                              ConverterJGONObjectInListData converterJGONObjectInListData) {

        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsDateBaseNewsSource = newsDateBaseNewsSource;
        this.newsSharedPrefDataSource = newsSharedPrefDataSource;
        this.converterJGONObjectInListData = converterJGONObjectInListData;
        this.context = ApplicationContextSingleton.getContext();
    }

    @Override
    public void onCompletedFromServer(boolean onFinished) {
        if (onFinished) {
            newsDateBaseNewsSource.setSubcriber(this);
            newsDateBaseNewsSource.loadNewsFromDataBase();
        }
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onCompletedFromDateBase(List<NewsItem> newsItemListFromDateBase) {
        callBackRepo.setData(newsItemListFromDateBase);
    }


    @Override
    public void getData(CallBacRepo callBackRepo) {
        this.callBackRepo = callBackRepo;
        if (isOnline()) {
            Set<String> channelListSet = newsSharedPrefDataSource.getChannelUrlList();
            //newsRemoteDataSource.setSubcriber(this);
            //newsRemoteDataSource.loadDataFromServer(channelList);
            final ArrayList<String> channellistArrayList = new ArrayList<>(channelListSet);

            NewsBroadcastReceiver newsBroadcastReceiver = new NewsBroadcastReceiver();
            newsRemoteDataSource.setSubcriber(this);
            // регистрируем BroadcastReceiver
            IntentFilter intentFilter = new IntentFilter(NewsIntentService.ACTION_NEWSINTENTSERVICE);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            context.registerReceiver(newsBroadcastReceiver, intentFilter);

            Intent intent = new Intent(context, NewsIntentService.class);
            intent.putStringArrayListExtra(KEY_SERVICE, channellistArrayList);
            context.startService(intent);


        } else {
            newsDateBaseNewsSource.setSubcriber(this);
            newsDateBaseNewsSource.loadNewsFromDataBase();
        }
    }

    @Override
    public void saveChannel(final String channelUrl) {
        Set<String> channelList = newsSharedPrefDataSource.getChannelUrlList();
        // newsRemoteDataSource.loadDataFromServer(channelList);
        //newsSharedPrefDataSource.putChannelInList(channelUrl);


    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
