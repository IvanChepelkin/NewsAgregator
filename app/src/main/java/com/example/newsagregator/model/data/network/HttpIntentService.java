package com.example.newsagregator.model.data.network;

import android.app.IntentService;
import android.content.Intent;
import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.ConverterJGONObjectInListData;
import com.example.newsagregator.model.domain.NewsEmptity;

import org.json.JSONObject;

import java.util.List;

public class HttpIntentService extends IntentService {
    HTTPConnections httpConnections;
    public final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    public final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";
    /**
     * Creates an HttpIntentService.  Invoked by your subclass's constructor.
     *
     */
    public HttpIntentService() {
        super("HttpIntentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
       httpConnections = Factory.createObjectHTTPConnections();
        JSONObject jsonObject = httpConnections.getHTTPData(RSS_to_GSON + RSS_link);
        System.out.println("листyyyy"+jsonObject.toString());
        ConverterJGONObjectInListData converter = new ConverterJGONObjectInListData();
        List<NewsEmptity> list = converter.setListModelView(jsonObject);

        System.out.println("лист"+list.toString());
        Factory.createObjectDataBaseNewsSource().addNewsInDataBase(list);

    }
}
