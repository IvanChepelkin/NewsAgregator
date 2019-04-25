package com.example.newsagregator.model;

import com.example.newsagregator.Factory;
import com.example.newsagregator.model.db.IGetSetNewsDataBase;
import com.example.newsagregator.model.network.IGetNewsFromRemote;
import com.example.newsagregator.presenter.IRepoNews;
import com.example.newsagregator.presenter.model_view.ModelView;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements IRepoNews, IGetNewsFromRemote.CallBackApi {

    private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";
    private List<ModelView> listModelView = new ArrayList<>();
    private FinishedListener finishedListener;
    private IGetNewsFromRemote getNewsFromRemote;
    private IGetSetNewsDataBase getDataFromBase;
    private ConverterJGONObjectInListData converterJGONObjectInListData;


    public DataManager(IGetNewsFromRemote getNewsFromRemote,
                       IGetSetNewsDataBase getDataFromBase,
                       ConverterJGONObjectInListData converterJGONObjectInListData) {

        this.getNewsFromRemote = getNewsFromRemote;
        this.getDataFromBase = getDataFromBase;
        this.converterJGONObjectInListData = converterJGONObjectInListData;
    }

    @Override
    public void onCompleted(JSONObject jsonObjectNews) {
        listModelView = converterJGONObjectInListData.setListModelView(jsonObjectNews);
        finishedListener.setData(listModelView);
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void getData(FinishedListener finishedListener) {
        this.finishedListener = finishedListener;
        getNewsFromRemote.setSubcriber(this);
        Factory.createObjectDataRemoteSource().execute(RSS_to_GSON + RSS_link);

    }

}
