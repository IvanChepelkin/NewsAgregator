package com.example.newsagregator.model.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.model.data.db.NewsDataBaseSource;
import com.example.newsagregator.model.data.network.NewsRemoteDataSource;
import com.example.newsagregator.model.domain.NewsItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsRepositoryImpl implements NewsRemoteDataSource.CallBackApi, NewsDataBaseSource.CallBackDb, NewsRepository {

    private List<NewsItem> listNewsItem = new ArrayList<>();
    private NewsRemoteDataSource newsRemoteDataSource;
    private NewsDataBaseSource dateBaseNewsSource;
    private NewsRepository.CallBacRepo callBackRepo;
    private ConverterJGONObjectInListData converterJGONObjectInListData;


    public NewsRepositoryImpl(NewsRemoteDataSource newsRemoteDataSource,
                              NewsDataBaseSource dateBaseNewsSource,
                              ConverterJGONObjectInListData converterJGONObjectInListData) {

        this.newsRemoteDataSource = newsRemoteDataSource;
        this.dateBaseNewsSource = dateBaseNewsSource;
        this.converterJGONObjectInListData = converterJGONObjectInListData;
    }

    @Override
    public void onCompletedFromServer(final JSONObject jsonObjectNews) {
        listNewsItem = converterJGONObjectInListData.setListModelView(jsonObjectNews);
        callBackRepo.setData(listNewsItem);
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onCompletedFromDateBase(final List<NewsItem> newsItemListFromDateBase) {
        callBackRepo.setData(newsItemListFromDateBase);
    }


    @Override
    public void getData(CallBacRepo callBackRepo) {
        this.callBackRepo = callBackRepo;
        if (isOnline()) {
            newsRemoteDataSource.setSubcriber(this);
            newsRemoteDataSource.loadDataFromServer();
        } else {
            dateBaseNewsSource.setSubcriber(this);
            dateBaseNewsSource.loadNewsFromDataBase();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) ApplicationContextSingleton.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
