package com.example.newsagregator.model.network;

import android.os.AsyncTask;
import org.json.JSONObject;


public class ServiceApi extends AsyncTask<String, String, JSONObject> implements IGetNoticeService {
    private IGetNoticeService.CallBackApi callBackApi;
    private static ServiceApi ServiceApiInstance;

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject result;
        HTTPConnection testHTTPConnection = new HTTPConnection();
        result = testHTTPConnection.getHTTPData(strings[0]);
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObjectNews) {
        callBackApi.onCompleted(jsonObjectNews);

    }

    public static ServiceApi getServiceApiInstance() {
        if (ServiceApiInstance == null) {
            ServiceApiInstance = new ServiceApi();
        }
        return ServiceApiInstance;
    }

    @Override
    public void setSubcriber(CallBackApi callBackApi) {
        this.callBackApi = callBackApi;
    }
}
