package com.example.newsagregator.model;

import com.example.newsagregator.model.network.IGetNoticeService;
import com.example.newsagregator.model.network.ServiceApi;
import com.example.newsagregator.model.network.models.NewsRssObject;
import com.example.newsagregator.presenter.INewsData;
import com.example.newsagregator.presenter.model_view.ModelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements INewsData, IGetNoticeService.CallBackApi {

    private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    public List<ModelView> listModelView = new ArrayList<>();
    private FinishedListener finishedListener;

    public DataManager() {
    }

    @Override
    public void onCompleted(JSONObject jsonObjectNews) {
        setListModelView(jsonObjectNews);
        finishedListener.setData(listModelView);
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void getData(FinishedListener finishedListener) {
        this.finishedListener = finishedListener;
        ServiceApi.getServiceApiInstance().setSubcriber(this);
        ServiceApi.getServiceApiInstance().execute(RSS_to_GSON + RSS_link);
    }

    private void setListModelView(JSONObject jsonObjectNews) {

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


    }

}
