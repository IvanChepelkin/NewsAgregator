package com.example.newsagregator.model.data.network;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HTTPConnections {
    private JSONObject stream = null;
    private static final String RESPONSE = "status";
    private static final String ALL_GOOD = "ok";

    public HTTPConnections() {
    }

    JSONObject getHTTPData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sbuilder = new StringBuilder(1024);
                String line;
                while ((line = reader.readLine()) != null) {
                    sbuilder.append(line).append("\n");
                }
                httpURLConnection.disconnect();
                final JSONObject jsonObject = new JSONObject(sbuilder.toString());
//                if (jsonObject.getString(RESPONSE) != ALL_GOOD) {
//                    return null;
//                } else {
//
//                }
                return jsonObject;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
