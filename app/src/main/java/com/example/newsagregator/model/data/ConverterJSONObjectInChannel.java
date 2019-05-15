package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.Channel.ChannelItem;
import org.json.JSONException;
import org.json.JSONObject;


public class ConverterJSONObjectInChannel {
    private ChannelItem channelItem;

    public ConverterJSONObjectInChannel() {
    }

    public ChannelItem setChannel(JSONObject jsonObjectNews) {

        try {
            JSONObject jsonObject = jsonObjectNews.getJSONObject("feed");

            channelItem = new ChannelItem(
                    jsonObject.getString("title"),
                    jsonObject.getString("url"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return channelItem;
    }
}