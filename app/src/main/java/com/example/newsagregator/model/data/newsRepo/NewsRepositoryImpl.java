package com.example.newsagregator.model.data.newsRepo;

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
import com.example.newsagregator.model.domain.News.CallBackNewsRepo;
import com.example.newsagregator.model.domain.News.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsRepositoryImpl implements NewsRemoteDataSource.CallBackApi, NewsDataBaseSource.NewsCallBackDb, NewsRepository {

    private NewsRemoteDataSource newsRemoteDataSource;
    private NewsDataBaseSource newsDateBaseNewsSource;
    private CallBackNewsRepo callBackNewsRepo;
    private Context context;
    private String KEY_SERVICE = "channels";

    public NewsRepositoryImpl(NewsRemoteDataSource newsRemoteDataSource) {

        this.newsRemoteDataSource = newsRemoteDataSource;
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
        callBackNewsRepo.setError(exeption);
    }

    @Override
    public void onCompletedFromDateBase(List<NewsItem> newsItemListFromDateBase) {
        callBackNewsRepo.setNewsItemList(newsItemListFromDateBase);
    }


    @Override
    public void subscribeNewsRepository(CallBackNewsRepo callBackNewsRepo) {
        this.callBackNewsRepo = callBackNewsRepo;
    }

    @Override
    public void getNews(List<String> channelList) {
        if (isOnline()) {
            loadNewsFromRemote(channelList);

        } else {
            loadNewsFromDataBase();
        }
    }

    private void loadNewsFromRemote(final List<String> channelList) {

        final ArrayList<String> channelslistArrayList = new ArrayList<>(channelList);
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
