package com.example.newsagregator.model.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.domain.NewsItem;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class NewsIntentService extends IntentService implements LoadDataHttp.CallBackHttp {
    public static final String EXTRA_KEY_SUCCESS = "SUCCESS";
    public static final String ACTION_NEWSINTENTSERVICE = "NEWS_FROM_INTENTSERVICE";
    public static final String EXTRA_KEY_ERROR = "EXEPTION";
    private LoadDataHttp loadDataHttp;
    private String KEY_SERVICE = "channels";
    private List<String> channellistArrayList;
    private final String API_KEY = "&api_key=ktqj6tz7a5tpcb3u5yqie1rxtvqyk0vb1t75fys9";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";
    private boolean onFinish;
    private List<NewsItem> newsItemlist;
    private String url;

    /**
     * Creates an NewsIntentService.  Invoked by your subclass's constructor.
     */
    public NewsIntentService() {
        super("NewsIntentService");
        this.loadDataHttp = Factory.createObjectHTTPConnections();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        channellistArrayList = intent.getStringArrayListExtra(KEY_SERVICE);
        for (String url : channellistArrayList) {
            this.url = url;
            if (url.equals(channellistArrayList.get(channellistArrayList.size() - 1))) {
                onFinish = true;
            }
            loadDataHttp.getHttpData(this, RSS_to_GSON + url + API_KEY);
        }
    }

    @Override
    public void onSuccess(JSONObject result) {

        ConverterJGONObjectInListData converter = new ConverterJGONObjectInListData();
        DataBaseHelper dataBaseHelper = Factory.createObjectDataBaseHelper();
        newsItemlist = converter.setListModelView(result);
        dataBaseHelper.addNewsInDataBase(newsItemlist, url);
        dataBaseHelper.addChannelInDataBase(url);

        if (onFinish) {
            Intent responseIntent = new Intent();
            responseIntent.setAction(ACTION_NEWSINTENTSERVICE);
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
            responseIntent.putExtra(EXTRA_KEY_SUCCESS, onFinish);
            sendBroadcast(responseIntent);
        }
    }

    @Override
    public void onError(Throwable exeption) {

        onFinish = false;
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_KEY_ERROR, exeption);
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION_NEWSINTENTSERVICE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(EXTRA_KEY_SUCCESS, onFinish);
        responseIntent.putExtra(EXTRA_KEY_SUCCESS, extras);
        sendBroadcast(responseIntent);
    }
}
