package com.example.newsagregator.model.domain.Channel.channel_save_usecase;

import com.example.newsagregator.model.data.channelRepo.ChannelRepository;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import io.reactivex.Single;

public class SaveChannelUseCaseImpl implements SaveChannelUseCase {

    private ChannelRepository channelRepository;

    public SaveChannelUseCaseImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }


    @Override
    public Single <ChannelItem> saveChannel(String channelToSave) {
        return channelRepository.saveChannel(channelToSave);
    }
}
