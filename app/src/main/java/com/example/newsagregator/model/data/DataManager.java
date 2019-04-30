package com.example.newsagregator.model.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.newsagregator.di.ApplicationContextSingleton;
import com.example.newsagregator.model.data.db.DataBaseNewsSource;
import com.example.newsagregator.model.data.network.RemoteNewsDataSource;
import com.example.newsagregator.model.domain.NewsEmptity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements RemoteNewsDataSource.CallBackApi, DataBaseNewsSource.CallBackDb, RepoNews {

    private List<NewsEmptity> listNewsEmptity = new ArrayList<>();
    private RemoteNewsDataSource remoteNewsDataSource;
    private DataBaseNewsSource dateBaseNewsSource;
    private RepoNews.CallBacRepo callBackRepo;
    private ConverterJGONObjectInListData converterJGONObjectInListData;


    public DataManager(RemoteNewsDataSource remoteNewsDataSource,
                       DataBaseNewsSource dateBaseNewsSource,
                       ConverterJGONObjectInListData converterJGONObjectInListData) {

        this.remoteNewsDataSource = remoteNewsDataSource;
        this.dateBaseNewsSource = dateBaseNewsSource;
        this.converterJGONObjectInListData = converterJGONObjectInListData;
    }

    @Override
    public void onCompletedFromServer(JSONObject jsonObjectNews) {
        listNewsEmptity = converterJGONObjectInListData.setListModelView(jsonObjectNews);
        callBackRepo.setData(listNewsEmptity);
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onCompletedFromDateBase(List<NewsEmptity> newsEmptityListFromDateBase) {
        callBackRepo.setData(newsEmptityListFromDateBase);
    }


    @Override
    public void getData(CallBacRepo callBackRepo) {
        this.callBackRepo = callBackRepo;
        if (isOnline()) {
            remoteNewsDataSource.setSubcriber(this);
            remoteNewsDataSource.loadDataFromServer();
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
