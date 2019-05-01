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
    HTTPConnections httpConnections;
    DataBaseHelper dataBaseHelper;
    public final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    public final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    /**
     * Creates an NewsIntentService.  Invoked by your subclass's constructor.
     */
    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        httpConnections = Factory.createObjectHTTPConnections();
        dataBaseHelper = Factory.createObjectDataBaseHelper();
        ConverterJGONObjectInListData converter = new ConverterJGONObjectInListData();

        JSONObject jsonObject = httpConnections.getHTTPData(RSS_to_GSON + RSS_link);
        final List<NewsItem> list = converter.setListModelView(jsonObject);
        dataBaseHelper.addNewsInDataBase(list);

    }
}
