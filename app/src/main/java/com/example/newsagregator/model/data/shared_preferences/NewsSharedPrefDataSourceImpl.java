package com.example.newsagregator.model.data.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class NewsSharedPrefDataSourceImpl implements NewsSharedPrefDataSource {
    private SharedPreferences channelListSheredPref;
    private Set<String> channelsList = new HashSet<>();
    private final String APP_PREFERENCES = "NEWS_CHANNELS";
    private final String CHANNEL_KEY = "NEWS_CHANNEL";

    public NewsSharedPrefDataSourceImpl(Context context) {
        channelListSheredPref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void putChannelInList(String channelUrl) {
        channelsList = getChannelsUrlList();
        if (!channelsList.contains(channelUrl)) {
            channelsList.add(channelUrl);
            changeListChannels();
        }
    }

    @Override
    public Set<String> getChannelsUrlList() {
        channelsList = channelListSheredPref.getStringSet(CHANNEL_KEY, channelsList);
        return channelsList;
    }

    @Override
    public void deleteChannel(final List<String> channelsToDeleteList) {
        channelsList = getChannelsUrlList();
        for (int i = 0; i < channelsToDeleteList.size(); i++) {
                channelsList.remove(channelsToDeleteList.get(i));
        }
        System.out.println("так удаляет или нет?"+channelsList);
        changeListChannels();
    }


    private void changeListChannels() {
        SharedPreferences.Editor editor = channelListSheredPref.edit();
        editor.remove(CHANNEL_KEY);
        editor.apply();
        editor.putStringSet(CHANNEL_KEY, channelsList);
        editor.apply();
    }

}
