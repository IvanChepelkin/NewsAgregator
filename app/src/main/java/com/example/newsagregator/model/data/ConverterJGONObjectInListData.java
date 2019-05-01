package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConverterJGONObjectInListData {
    public ConverterJGONObjectInListData() {
    }

    public List<NewsItem> setListModelView(JSONObject jsonObjectNews) {
        List<NewsItem> listNewsItem = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObjectNews.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                listNewsItem.add(new NewsItem(
                        jsonArray.getJSONObject(i).getString("title"),
                        jsonArray.getJSONObject(i).getString("guid"),
                        jsonArray.getJSONObject(i).getString("content")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listNewsItem;
    }
}
