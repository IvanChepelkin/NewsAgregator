package com.example.newsagregator.model.network;

import android.os.AsyncTask;

import com.example.newsagregator.model.network.models.NewsRssObject;
import com.google.gson.Gson;

public class ServiceApi extends AsyncTask<String, String, String> implements IGetNoticeService {

    private NewsRssObject newsRssObject;
    private IGetNoticeService.CallBackApi callBackApi;
    private static ServiceApi ServiceApiInstance;

    @Override
    protected String doInBackground(String... strings) {
        String result;
        HTTPConnection testHTTPConnection = new HTTPConnection();
        result = testHTTPConnection.getHTTPData(strings[0]);
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        newsRssObject = new Gson().fromJson(s, NewsRssObject.class);
        callBackApi.onCompleted(newsRssObject);
    }

    public static ServiceApi getServiceApiInstance() {
        if (ServiceApiInstance == null) {
            ServiceApiInstance = new ServiceApi();
        }
        return ServiceApiInstance;
    }

    @Override
    public void setSubcriber(CallBackApi callBackApi) {
        this.callBackApi = callBackApi;
    }
}
