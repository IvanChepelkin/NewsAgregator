package com.example.newsagregator.model.data.network;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.domain.NewsItem;

import org.json.JSONObject;

import java.util.List;
import java.util.Set;


public class NewsRemoteDataSourceImpl extends AsyncTask<Set<String>, String, Boolean> implements NewsRemoteDataSource {

    //private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String API_KEY = "&api_key=ktqj6tz7a5tpcb3u5yqie1rxtvqyk0vb1t75fys9";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";
    private HTTPConnections httpConnections;
    private CallBackApi callBackApi;

    public NewsRemoteDataSourceImpl(HTTPConnections httpConnections) {
        this.httpConnections = httpConnections;
    }

//    @Override
//    protected JSONObject doInBackground(String... strings) {
//        JSONObject result;
//        httpConnections = Factory.createObjectHTTPConnections();
//        result = httpConnections.getHTTPData(strings[0]);
//
//        DataBaseHelper dataBaseHelper = Factory.createObjectDataBaseHelper();
//        ConverterJGONObjectInListData converter = new ConverterJGONObjectInListData();
//        final List<NewsItem> list = converter.setListModelView(result);
//        dataBaseHelper.addNewsInDataBase(list);
//
//        return result;
//    }

    @Override
    protected Boolean doInBackground(Set<String>... sets) {

        httpConnections = Factory.createObjectHTTPConnections();
        for (String url : sets[0]) {
            JSONObject result = httpConnections.getHTTPData(RSS_to_GSON + url + API_KEY);
            DataBaseHelper dataBaseHelper = Factory.createObjectDataBaseHelper();
            ConverterJGONObjectInListData converter = new ConverterJGONObjectInListData();
            List<NewsItem> list = converter.setListModelView(result);
            dataBaseHelper.addNewsInDataBase(list);
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean onFinishedLoad) {
        callBackApi.onCompletedFromServer(onFinishedLoad);

    }

    @Override
    public void setSubcriber(CallBackApi callBackApi) {
        this.callBackApi = callBackApi;
    }

    @Override
    public void loadDataFromServer(Set<String> channelList) {
        Factory.createObjectDataRemoteSource().execute(channelList);

    }

}
