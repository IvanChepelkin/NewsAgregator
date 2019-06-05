package com.example.newsagregator.model.data.network;

import org.json.JSONObject;

public interface LoadDataHttp {

    JSONObject getHttpData( String urlChannel);

}