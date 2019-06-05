package com.example.newsagregator.model.domain.Channel.channel_usecase;

import com.example.newsagregator.model.data.channelRepo.ChannelRepository;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;

import java.util.List;

import io.reactivex.Single;

public class GetGetChannelUseCaseImpl implements GetChannelUseCase {

    private ChannelRepository channelRepository;

    public GetGetChannelUseCaseImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }
    @Override
    public Single<List<ChannelItem>> getChannels() {
        return channelRepository.getChannels();
    }

}
