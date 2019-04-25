package com.example.newsagregator.model;

import com.example.newsagregator.presenter.model_view.ModelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConverterJGONObjectInListData {
    public ConverterJGONObjectInListData() {
    }

    public List<ModelView> setListModelView(JSONObject jsonObjectNews) {
        List<ModelView> listModelView = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObjectNews.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {

                listModelView.add(new ModelView(
                        jsonArray.getJSONObject(i).getString("title"),
                        jsonArray.getJSONObject(i).getString("guid"),
                        jsonArray.getJSONObject(i).getString("content")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listModelView;
    }
}
