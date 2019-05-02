package com.example.newsagregator.model.data.db;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsDataBaseSourceImpl extends AsyncTask<Void, Void, List<NewsItem>> implements NewsDataBaseSource {
    private DataBaseHelper dataBaseHelper;
    private CallBackDb callBackDb;

    public NewsDataBaseSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    protected List<NewsItem> doInBackground(Void... voids) {
        dataBaseHelper = Factory.createObjectDataBaseHelper();
        List<NewsItem> newsItemList = new ArrayList<>();
        newsItemList = dataBaseHelper.getNewsFromDataBase();
        return newsItemList;
    }

    @Override
    protected void onPostExecute(final List<NewsItem> newsItemList) {
        for (int i = 0; i < newsItemList.size(); i++) {
            System.out.println("Листок " + newsItemList.get(i).getGuide());
        }

        callBackDb.onCompletedFromDateBase(newsItemList);
    }

    @Override
    public void loadNewsFromDataBase() {
        execute();
    }

    @Override
    public void setSubcriber(CallBackDb callBackDb) {
        this.callBackDb = callBackDb;
    }
}
