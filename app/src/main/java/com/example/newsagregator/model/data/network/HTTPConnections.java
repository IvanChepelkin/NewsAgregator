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


public class HTTPConnections implements LoadDataHttp {
    private CallBackHttp callBackHttp;

    public HTTPConnections() {
    }


    @Override
    public void getHttpData(CallBackHttp callBackHttp, String urlChannel) {
        this.callBackHttp = callBackHttp;
        try {
            URL url = new URL(urlChannel);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sbuilder = new StringBuilder(1024);
            String line;
            while ((line = reader.readLine()) != null) {
                sbuilder.append(line).append("\n");
            }
            httpURLConnection.disconnect();
            final JSONObject result = new JSONObject(sbuilder.toString());
            callBackHttp.onSuccess(result);
            //}

        } catch (MalformedURLException e) {
            callBackHttp.onError(e);
        }
        catch (IOException e) {
            callBackHttp.onError(e);
        }
        catch (JSONException e) {
            callBackHttp.onError(e);
        }
    }
}
