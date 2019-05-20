package com.example.newsagregator.model.domain.Channel;

import com.example.newsagregator.model.data.channelRepo.ChannelRepository;

import java.util.List;

public class ChannelUseCaseImpl implements SubscribeChannelUseCase,ChannelUseCase, CallBackChannelRepo {

    private ChannelRepository channelRepository;
    private ChannelPresenterListener channelPresenterListener;
    private String channelSaveUrl;
    private List<ChannelItem> channelItemListList;

    public ChannelUseCaseImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
        channelRepository.subscribeChannelRepository(this);
    }


    @Override
    public void deleteChannel(List<String> channelsToDeleteList) {
        channelRepository.deleteChannels(channelsToDeleteList);
    }

    @Override
    public void getChannels() {
       channelRepository.getChannels();
    }

    @Override
    public void setChannelList(List<ChannelItem> channelListSet) {
        channelPresenterListener.setChannelsItemList(channelListSet);

    }

    @Override
    public void ChannelsDeleteCompleted(Boolean onFinishDeleteChannels) {
        if (onFinishDeleteChannels) {
            channelPresenterListener.ChannelsDeleteCompleted(onFinishDeleteChannels);
        }
    }

    @Override
    public void subscribePresenterChannels(ChannelPresenterListener channelPresenterListener) {
        this.channelPresenterListener = channelPresenterListener;

    }
}
