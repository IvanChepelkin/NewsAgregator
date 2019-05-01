package com.example.newsagregator.model.data.shared_preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;


public class NewsSharedPrefDataSourceImpl implements NewsSharedPrefDataSource {
    private SharedPreferences channelListSheredPref;
    private Set<String> channelList = new HashSet<>();
    private final String APP_PREFERENCES = "NEWS_CHANNELS";
    private final String CHANNEL_KEY = "NEWS_CHANNEL";

    public NewsSharedPrefDataSourceImpl(Context context) {
        channelListSheredPref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void putChannelInList(String channelUrl) {
        channelList = getChannelUrlList();
        if (!channelList.contains(channelUrl)) {
            channelList.add(channelUrl);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = channelListSheredPref.edit();
            editor.remove(CHANNEL_KEY);
            editor.putStringSet(CHANNEL_KEY, channelList);
            editor.apply();
        }

    }

    @Override
    public Set<String> getChannelUrlList() {
        channelList = channelListSheredPref.getStringSet(CHANNEL_KEY, new HashSet<String>());
        return channelList;
    }

    @Override
    public void deleteChannel(String channelUrl) {
        channelList = getChannelUrlList();
        channelList.remove(channelUrl);
        SharedPreferences.Editor editor = channelListSheredPref.edit();
        editor.remove(CHANNEL_KEY);
        editor.putStringSet(CHANNEL_KEY, channelList);
        editor.apply();
    }

}
