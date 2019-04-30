package com.example.newsagregator.model.data.network;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.domain.NewsEmptity;

import org.json.JSONObject;

import java.util.List;


public class RemoteNewsDataSourceIml extends AsyncTask<String, String, JSONObject> implements RemoteNewsDataSource {

    private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";
    private HTTPConnections httpConnections;
    private CallBackApi callBackApi;

    public RemoteNewsDataSourceIml(HTTPConnections httpConnections) {
        this.httpConnections = httpConnections;
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject result;
        httpConnections = Factory.createObjectHTTPConnections();
        result = httpConnections.getHTTPData(strings[0]);

        DataBaseHelper dataBaseHelper = Factory.createObjectDataBaseHelper();
        ConverterJGONObjectInListData converter = new ConverterJGONObjectInListData();
        List<NewsEmptity> list = converter.setListModelView(result);
        dataBaseHelper.addNewsInDataBase(list);

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObjectNews) {
        callBackApi.onCompletedFromServer(jsonObjectNews);

    }

    @Override
    public void setSubcriber(CallBackApi callBackApi) {
        this.callBackApi = callBackApi;
    }

    @Override
    public void loadDataFromServer() {
        Factory.createObjectDataRemoteSource().execute(RSS_to_GSON + RSS_link);
    }

}
