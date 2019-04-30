package com.example.newsagregator.model.data;

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
        //callBackRepo.setData(listNewsEmptity);
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
        //remoteNewsDataSource.setSubcriber(this);
       // remoteNewsDataSource.loadDataFromServer();

        dateBaseNewsSource.setSubcriber(this);
        dateBaseNewsSource.loadNewsFromDataBase();
    }


}
