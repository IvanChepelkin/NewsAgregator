package com.example.newsagregator.model.data;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.db.DataBaseNewsSource;
import com.example.newsagregator.model.data.network.RemoteNewsDataSource;
import com.example.newsagregator.model.domain.GetNewsUseCase;
import com.example.newsagregator.model.domain.NewsEmptity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements RemoteNewsDataSource.CallBackApi, RepoNews {

    private List<NewsEmptity> listNewsEmptity = new ArrayList<>();
    private RemoteNewsDataSource remoteNewsDataSource;
    private DataBaseNewsSource DateBaseNewsSource;
    private CallBacRepo callBackRepo;
    private ConverterJGONObjectInListData converterJGONObjectInListData;


    public DataManager(RemoteNewsDataSource remoteNewsDataSource,
                       DataBaseNewsSource dateBaseNewsSource,
                       ConverterJGONObjectInListData converterJGONObjectInListData) {

        this.remoteNewsDataSource = remoteNewsDataSource;
        this.DateBaseNewsSource = DateBaseNewsSource;
        this.converterJGONObjectInListData = converterJGONObjectInListData;
    }

    @Override
    public void onCompleted(JSONObject jsonObjectNews) {
        listNewsEmptity = converterJGONObjectInListData.setListModelView(jsonObjectNews);
        callBackRepo.setData(listNewsEmptity);
    }

    @Override
    public void onError(Throwable t) {
    }


    @Override
    public void getData(CallBacRepo callBackRepo) {
        this.callBackRepo = callBackRepo;
        remoteNewsDataSource.setSubcriber(this);
        remoteNewsDataSource.loadData();
    }
}
