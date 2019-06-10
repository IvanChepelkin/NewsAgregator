package com.example.newsagregator.model.data.network;

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


public class HTTPConnectImpl implements HttpConnect {

    public HTTPConnectImpl() {
    }

    @Override
    public JSONObject getJsonObjectNews(final String urlChannel) {
        try {
            URL url = new URL(urlChannel);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sbuilder = new StringBuilder(1024);
            String line;
            while ((line = reader.readLine()) != null) {
                sbuilder.append(line).append("\n");
            }
            httpURLConnection.disconnect();
            final JSONObject result = new JSONObject(sbuilder.toString());

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
