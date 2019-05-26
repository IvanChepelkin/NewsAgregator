package com.example.newsagregator.model.data.newsRepo.news_converter;

import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConverterJONObjectInListData {
    public ConverterJONObjectInListData() {
    }

    public List<NewsItem> setListModelView(JSONObject jsonObjectNews) {
        List<NewsItem> listNewsItem = new ArrayList<>();

        try {
            JSONArray jsonItems = jsonObjectNews.getJSONArray("items");
            JSONObject jsonFeed = jsonObjectNews.getJSONObject("feed");
            for (int i = 0; i < jsonItems.length(); i++) {

                listNewsItem.add(new NewsItem(jsonFeed.getString("title"),
                        jsonItems.getJSONObject(i).getString("pubDate"),
                        jsonItems.getJSONObject(i).getString("title"),
                        jsonItems.getJSONObject(i).getString("link"),
                        jsonItems.getJSONObject(i).getString("description")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listNewsItem;
    }
}
