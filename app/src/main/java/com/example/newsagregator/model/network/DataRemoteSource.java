package com.example.newsagregator.model.network;

import android.os.AsyncTask;

import com.example.newsagregator.Factory;

import org.json.JSONObject;


public class DataRemoteSource extends AsyncTask<String, String, JSONObject> implements IGetNewsFromRemote {
    private HTTPConnections httpConnections;
    private CallBackApi callBackApi;

    public DataRemoteSource(HTTPConnections httpConnections){
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
        callBackApi.onCompleted(jsonObjectNews);

    }

    @Override
    public void setSubcriber(CallBackApi callBackApi) {
        this.callBackApi = callBackApi;
    }
}
