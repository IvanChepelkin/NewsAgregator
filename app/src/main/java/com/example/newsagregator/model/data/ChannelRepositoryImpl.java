package com.example.newsagregator.model.data;

import com.example.newsagregator.di.Factory;
import com.example.newsagregator.model.data.db.ChannelLoadDataBaseSource;
import com.example.newsagregator.model.data.db.ChannelsDeleteDataBaseSource;
import com.example.newsagregator.model.data.network.NewsRemoteDataSource;
import com.example.newsagregator.model.domain.Channel.CallBackChannelRepo;
import com.example.newsagregator.model.domain.Channel.ChannelItem;

import java.util.List;

public class ChannelRepositoryImpl implements ChannelRepository,
        ChannelLoadDataBaseSource.ChannelsLoadCallBackDb,
        ChannelsDeleteDataBaseSource.ChannelsDeleteCallBackDb {

    private NewsRemoteDataSource newsRemoteDataSource;
    private ChannelLoadDataBaseSource channelDataBaseSource;
    private ChannelsDeleteDataBaseSource channelsDeleteDataBaseSource;
    private CallBackChannelRepo callBackChannelRepo;

    public ChannelRepositoryImpl(NewsRemoteDataSource newsRemoteDataSource,
                                 ChannelLoadDataBaseSource channelDataBaseSource,
                                 ChannelsDeleteDataBaseSource channelsDeleteDataBaseSource)
    {
        this.newsRemoteDataSource = newsRemoteDataSource;
        this.channelDataBaseSource = channelDataBaseSource;
        this.channelsDeleteDataBaseSource = channelsDeleteDataBaseSource;
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
        channelsDeleteDataBaseSource = Factory.createObjectChannelsDeleteDataBaseSourceImpl();
        channelsDeleteDataBaseSource.setSubcriber(this);
        channelsDeleteDataBaseSource.deleteChannelsFromDataBase(channelsToDeleteList);

    }

    @Override
    public void getChannels() {

        channelDataBaseSource.setSubcriber(this);

        channelDataBaseSource.loadChannelsFromDataBase();
    }


    @Override
    public void ChannelsLoadCompletedFromDateBase(List<ChannelItem> channelItemListFromDateBase) {
        callBackChannelRepo.setChannelList(channelItemListFromDateBase);
    }


    @Override
    public void ChannelsDeleteCompletedFromDateBase(Boolean onFinishDeleteChannels) {

    }
}
