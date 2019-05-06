package com.example.newsagregator.model.data.network;

import android.app.IntentService;
import android.content.Intent;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.data.db.DataBaseHelper;
import com.example.newsagregator.model.domain.NewsItem;

import org.json.JSONObject;

import java.util.List;

public class NewsIntentService extends IntentService {
    public static final String ACTION_NEWSINTENTSERVICE = "NEWS_FROM_INTENTSERVICE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    private String KEY_SERVICE = "channels";
    private List<String> channellistArrayList;
    private final String API_KEY = "&api_key=ktqj6tz7a5tpcb3u5yqie1rxtvqyk0vb1t75fys9";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    /**
     * Creates an NewsIntentService.  Invoked by your subclass's constructor.
     */
    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        boolean onFinish;

        channellistArrayList = intent.getStringArrayListExtra(KEY_SERVICE);
        for (String url : channellistArrayList) {
            HTTPConnections httpConnections = Factory.createObjectHTTPConnections();
            DataBaseHelper dataBaseHelper = Factory.createObjectDataBaseHelper();
            JSONObject result = httpConnections.getHTTPData(RSS_to_GSON + url + API_KEY);
            ConverterJGONObjectInListData converter = new ConverterJGONObjectInListData();
            List<NewsItem> list = converter.setListModelView(result);
            dataBaseHelper.addNewsInDataBase(list);
        }
        onFinish = true;

        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION_NEWSINTENTSERVICE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        responseIntent.putExtra(EXTRA_KEY_OUT, onFinish);
        sendBroadcast(responseIntent);

    }
}
