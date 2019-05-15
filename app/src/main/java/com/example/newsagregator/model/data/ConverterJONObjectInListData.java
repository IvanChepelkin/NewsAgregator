package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.News.NewsItem;

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
            JSONArray jsonArray = jsonObjectNews.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                listNewsItem.add(new NewsItem(
                        jsonArray.getJSONObject(i).getString("title"),
                        jsonArray.getJSONObject(i).getString("link"),
                        jsonArray.getJSONObject(i).getString("description")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listNewsItem;
    }
}