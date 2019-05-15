package com.example.newsagregator.model.data;

import com.example.newsagregator.model.data.db.ChannelDataBaseSource;
import com.example.newsagregator.model.data.network.NewsRemoteDataSource;
import com.example.newsagregator.model.domain.Channel.CallBackChannelRepo;
import com.example.newsagregator.model.domain.Channel.ChannelItem;

import java.util.List;

public class ChannelRepositoryImpl implements ChannelRepository, ChannelDataBaseSource.ChannelsCallBackDb {
    private NewsRemoteDataSource newsRemoteDataSource;
    private ChannelDataBaseSource channelDataBaseSource;
    private CallBackChannelRepo callBackChannelRepo;

    public ChannelRepositoryImpl(NewsRemoteDataSource newsRemoteDataSource, ChannelDataBaseSource channelDataBaseSource) {
        this.newsRemoteDataSource = newsRemoteDataSource;
        this.channelDataBaseSource = channelDataBaseSource;
    }

    @Override
    public void subscribeChannelRepository(CallBackChannelRepo callBackChannelRepo) {
        this.callBackChannelRepo = callBackChannelRepo;
    }

    @Override
    public void saveChannel(final String channelUrl) {
//        newsSharedPrefDataSource.putChannelInList(channelUrl);
        //loadNewsFromRemote();
    }

    @Override
    public void deleteChannels(final List<String> channelsToDeleteList) {
        //newsSharedPrefDataSource.deleteChannel(channelsToDeleteList);
    }

    @Override
    public void getChannels() {

        channelDataBaseSource.setSubcriber(this);

        channelDataBaseSource.loadChannelsFromDataBase();
    }


    @Override
    public void ChannelsonCompletedFromDateBase(List<ChannelItem> channelItemListFromDateBase) {
        callBackChannelRepo.setChannelList(channelItemListFromDateBase);
    }
}
