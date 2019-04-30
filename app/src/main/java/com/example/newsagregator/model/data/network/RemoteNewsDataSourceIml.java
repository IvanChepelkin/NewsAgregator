package com.example.newsagregator.model.data.network;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;

import org.json.JSONObject;


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
