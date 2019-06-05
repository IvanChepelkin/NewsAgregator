package com.example.newsagregator.model.domain.Channel.channel_save_usecase;

import com.example.newsagregator.model.data.channelRepo.ChannelRepository;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import io.reactivex.Single;

public class ChannelSaveUseCaseImpl implements ChannelSaveUseCase {

    private ChannelRepository channelRepository;

    public ChannelSaveUseCaseImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }


    @Override
    public Single <ChannelItem> saveChannels(String channelToSave) {
        return channelRepository.saveChannels(channelToSave);
    }
}
