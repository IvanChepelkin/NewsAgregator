package com.example.newsagregator.model.network.intent_service;

import android.app.IntentService;
import android.content.Intent;

import com.example.newsagregator.model.network.HTTPConnection;

import org.json.JSONObject;

public class NewsService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NewsService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        JSONObject result;
        HTTPConnection testHTTPConnection = new HTTPConnection();
    }
}
