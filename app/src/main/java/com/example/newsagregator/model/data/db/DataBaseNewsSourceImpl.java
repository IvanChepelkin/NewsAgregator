package com.example.newsagregator.model.data.db;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.NewsEmptity;

import java.util.ArrayList;
import java.util.List;

public class DataBaseNewsSourceImpl extends AsyncTask<Void, Void, List<NewsEmptity>> implements DataBaseNewsSource {
    private DataBaseHelper dataBaseHelper;
    private CallBackDb callBackDb;

    public DataBaseNewsSourceImpl(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    protected List<NewsEmptity> doInBackground(Void... voids) {
        List<NewsEmptity> newsEmptityList = new ArrayList<>();
        newsEmptityList = Factory.createObjectDataBaseHelper().getNewsFromDataBase();
        return newsEmptityList;
    }

    @Override
    protected void onPostExecute(List<NewsEmptity> newsEmptityList) {
        System.out.println("Листок " + newsEmptityList.toString());
        callBackDb.onCompletedFromDateBase(newsEmptityList);
    }

    @Override
    public void loadNewsFromDataBase() {
        Factory.createObjectDataBaseNewsSourceImpl().execute();
    }

    @Override
    public void setSubcriber(CallBackDb callBackDb) {
        this.callBackDb = callBackDb;
    }
}
