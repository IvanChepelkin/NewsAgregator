package com.example.newsagregator.model.domain.Channel.channel_usecase;

import com.example.newsagregator.model.data.channelRepo.ChannelRepository;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Single;

public class ChannelUseCaseImpl implements ChannelUseCase {

    private ChannelRepository channelRepository;

    public ChannelUseCaseImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }
    @Override
    public Single<List<ChannelItem>> getChannels() {
        return channelRepository.getChannels();
    }

}
