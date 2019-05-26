package com.example.newsagregator.model.data.channelRepo.channel_converter;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
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
