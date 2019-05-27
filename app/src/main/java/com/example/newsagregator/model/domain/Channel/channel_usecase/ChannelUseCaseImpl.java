package com.example.newsagregator.model.domain.Channel.channel_usecase;

import com.example.newsagregator.model.data.channelRepo.ChannelRepository;
import com.example.newsagregator.model.domain.Channel.CallBackChannelRepo;
import com.example.newsagregator.model.domain.Channel.ChannelPresenterListener;
import com.example.newsagregator.model.domain.Channel.SubscribeChannelUseCase;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Single;

public class ChannelUseCaseImpl implements SubscribeChannelUseCase, ChannelUseCase, CallBackChannelRepo {

    private ChannelRepository channelRepository;
    private ChannelPresenterListener channelPresenterListener;

    public ChannelUseCaseImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
        channelRepository.subscribeChannelRepository(this);
    }

    @Override
    public void deleteChannels(List<String> channelsToDeleteList) {
        channelRepository.deleteChannels(channelsToDeleteList);
    }

    @Override
    public Single<List<ChannelItem>> getChannels() {
        return channelRepository.getChannels();
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
