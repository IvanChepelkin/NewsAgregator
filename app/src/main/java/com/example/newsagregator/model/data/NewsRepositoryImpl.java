package com.example.newsagregator.model.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.db.NewsDataBaseSource;
import com.example.newsagregator.model.data.network.NewsIntentService;
import com.example.newsagregator.model.data.network.NewsRemoteDataSource;
import com.example.newsagregator.model.data.shared_preferences.NewsSharedPrefDataSource;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NewsRepositoryImpl implements NewsRemoteDataSource.CallBackApi, NewsDataBaseSource.CallBackDb, NewsRepository {

    private NewsRemoteDataSource newsRemoteDataSource;
    private NewsDataBaseSource newsDateBaseNewsSource;
    private NewsSharedPrefDataSource newsSharedPrefDataSource;
    private CallBackRepo callBackRepo;
    private Context context;
    private String KEY_SERVICE = "channels";
    private Set<String> channelListSet;


    public NewsRepositoryImpl(NewsRemoteDataSource newsRemoteDataSource,
                              NewsSharedPrefDataSource newsSharedPrefDataSource) {

        this.newsRemoteDataSource = newsRemoteDataSource;
        this.newsSharedPrefDataSource = newsSharedPrefDataSource;
        this.context = ApplicationContextSingleton.getContext();
    }

    @Override
    public void onCompletedFromServer(boolean onFinished) {
        if (onFinished) {
            loadNewsFromDataBase();
        }
    }

    @Override
    public void onError(Throwable exeption) {
        callBackRepo.setError(exeption);
    }

    @Override
    public void onCompletedFromDateBase(List<NewsItem> newsItemListFromDateBase) {
        callBackRepo.setData(newsItemListFromDateBase);
    }


    @Override
    public void getData(CallBackRepo callBackRepo) {
        this.callBackRepo = callBackRepo;
        if (isOnline()) {
            loadNewsFromRemote();

        } else {
            loadNewsFromDataBase();
        }
    }

    @Override
    public void saveChannel(final String channelUrl) {
        newsSharedPrefDataSource.putChannelInList(channelUrl);
        loadNewsFromRemote();
    }

    @Override
    public void returnChannelsList() {
        channelListSet = newsSharedPrefDataSource.getChannelsUrlList();
        callBackRepo.setChannelList(channelListSet);
    }

    @Override
    public void deleteChannels(final List<String> channelsToDeleteList) {
        newsSharedPrefDataSource.deleteChannel(channelsToDeleteList);
    }

    private void loadNewsFromRemote() {
        channelListSet = newsSharedPrefDataSource.getChannelsUrlList();
        final ArrayList<String> channelslistArrayList = new ArrayList<>(channelListSet);
        // регистрируем BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(NewsIntentService.ACTION_NEWSINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        context.registerReceiver((BroadcastReceiver) newsRemoteDataSource, intentFilter);
        newsRemoteDataSource.setSubcriber(this);

        Intent intent = new Intent(context, NewsIntentService.class);
        intent.putStringArrayListExtra(KEY_SERVICE, channelslistArrayList);
        context.startService(intent);
    }

    private void loadNewsFromDataBase() {
        newsDateBaseNewsSource = Factory.createObjectDataBaseNewsSourceImpl();
        newsDateBaseNewsSource.setSubcriber(this);
        newsDateBaseNewsSource.loadNewsFromDataBase();
    }


    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
