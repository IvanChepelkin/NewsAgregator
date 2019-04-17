package com.example.newsagregator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.newsagregator.model.NewsRssObject;
import com.example.newsagregator.service.TestHTTPConnection;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    NewsRssObject newsRssObject;
    private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String RSS_to_GSON = " https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadRSS();
    }

    private void loadRSS() {
        AsyncTask<String, String, String> loadData = new AsyncTask<String, String, String>() {

            @Override
            protected String doInBackground(String... strings) {
                String result;
                TestHTTPConnection testHTTPConnection = new TestHTTPConnection();
                result = testHTTPConnection.getHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                newsRssObject = new Gson().fromJson(s,NewsRssObject.class);

            }
        };
        StringBuilder url_get_data = new StringBuilder(RSS_to_GSON);
        url_get_data.append(RSS_link);
        loadData.execute(url_get_data.toString());

    }
}
