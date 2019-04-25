package com.example.newsagregator.model;

import com.example.newsagregator.Factory;
import com.example.newsagregator.model.db.IGetSetNewsDataBase;
import com.example.newsagregator.model.network.IGetNewsFromRemote;
import com.example.newsagregator.presenter.IRepoNews;
import com.example.newsagregator.presenter.model_view.ModelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements IRepoNews, IGetNewsFromRemote.CallBackApi {

    private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";
    public List<ModelView> listModelView = new ArrayList<>();
    private FinishedListener finishedListener;
    private IGetNewsFromRemote getNewsFromRemote;
    private IGetSetNewsDataBase getDataFromBase;


    public DataManager(IGetNewsFromRemote getNewsFromRemote, IGetSetNewsDataBase getDataFromBase) {
        this.getNewsFromRemote = getNewsFromRemote;
        this.getDataFromBase = getDataFromBase;
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
        getNewsFromRemote.setSubcriber(this);
        Factory.createObjectDataRemoteSource().execute(RSS_to_GSON + RSS_link);

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
