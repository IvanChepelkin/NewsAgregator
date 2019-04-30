package com.example.newsagregator.model.data.db;

import android.os.AsyncTask;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.domain.NewsEmptity;

import java.util.ArrayList;
import java.util.List;

public class DataBaseReader extends AsyncTask<Void, Void, List<NewsEmptity>> {
    public DataBaseReader() {
    }

    @Override
    protected List<NewsEmptity> doInBackground(Void... voids) {
        List<NewsEmptity> newsEmptityList = new ArrayList<>();
        newsEmptityList = Factory.createObjectDataBaseNewsSource().getNewsFromDataBase();
        return newsEmptityList;
    }

    @Override
    protected void onPostExecute(List<NewsEmptity> newsEmptities) {
        System.out.println("Листок " + newsEmptities.toString());
    }
}
