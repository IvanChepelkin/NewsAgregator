package com.example.newsagregator.model.data;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.db.DataBaseNewsSource;
import com.example.newsagregator.model.data.network.RemoteNewsDataSource;
import com.example.newsagregator.presenter.IRepoNews;
import com.example.newsagregator.model.domain.NewsEmptity;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class DataManager implements IRepoNews, RemoteNewsDataSource.CallBackApi {

    private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";
    private List<NewsEmptity> listNewsEmptity = new ArrayList<>();
    private FinishedListener finishedListener;
    private RemoteNewsDataSource RemoteNewsDataSource;
    private DataBaseNewsSource DateBaseNewsSource;
    private ConverterJGONObjectInListData converterJGONObjectInListData;


    public DataManager(RemoteNewsDataSource gRemoteNewsDataSource,
                       DataBaseNewsSource  dateBaseNewsSource,
                       ConverterJGONObjectInListData converterJGONObjectInListData) {

        this.RemoteNewsDataSource = gRemoteNewsDataSource;
        this.DateBaseNewsSource = DateBaseNewsSource;
        this.converterJGONObjectInListData = converterJGONObjectInListData;
    }

    @Override
    public void onCompleted(JSONObject jsonObjectNews) {
        listNewsEmptity = converterJGONObjectInListData.setListModelView(jsonObjectNews);
        finishedListener.setData(listNewsEmptity);
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void getData(FinishedListener finishedListener) {
        this.finishedListener = finishedListener;
        RemoteNewsDataSource.setSubcriber(this);
        Factory.createObjectDataRemoteSource().execute(RSS_to_GSON + RSS_link);

    }

}
