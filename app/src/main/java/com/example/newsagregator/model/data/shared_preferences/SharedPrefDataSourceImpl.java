package com.example.newsagregator.model.data.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SharedPrefDataSourceImpl implements ChannelsSharedPrefDataSource {
    private SharedPreferences channelListSheredPref;
    private Set<String> channelsList = new HashSet<>();
    private final String APP_PREFERENCES = "NEWS_CHANNELS";
    private final String CHANNEL_KEY = "NEWS_CHANNEL";

    public SharedPrefDataSourceImpl(Context context) {
        channelListSheredPref = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void saveChannelInList(String channelUrl) {
        if (channelUrl != null) {
            channelsList = getChannelsUrlList();
            if (!channelsList.contains(channelUrl)) {
                channelsList.add(channelUrl);
                changeListChannels();
            }
        }
    }

    @Override
    public Set<String> getChannelsUrlList() {
        channelsList = channelListSheredPref.getStringSet(CHANNEL_KEY, channelsList);
        return channelsList;
    }

    @Override
    public void deleteChannelsList(final List<String> channelsToDeleteList) {
        channelsList = getChannelsUrlList();
        for (int i = 0; i < channelsToDeleteList.size(); i++) {
            channelsList.remove(channelsToDeleteList.get(i));
        }
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
