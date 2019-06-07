package com.example.newsagregator.model.data.network;

import org.json.JSONObject;

public interface HttpConnect {

    JSONObject getJsonObjectNews(String urlChannel);

}