package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsEmptity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConverterJGONObjectInListData {
    public ConverterJGONObjectInListData() {
    }

    public List<NewsEmptity> setListModelView(JSONObject jsonObjectNews) {
        List<NewsEmptity> listNewsEmptity = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObjectNews.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                listNewsEmptity.add(new NewsEmptity(
                        jsonArray.getJSONObject(i).getString("title"),
                        jsonArray.getJSONObject(i).getString("guid"),
                        jsonArray.getJSONObject(i).getString("content")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listNewsEmptity;
    }
}
