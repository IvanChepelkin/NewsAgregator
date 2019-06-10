package com.example.newsagregator.model.domain.Channel.channel_delete_usecase;

import com.example.newsagregator.model.data.channelRepo.ChannelRepository;

import java.util.List;

import io.reactivex.Completable;

public class DeleteChannelUseCaseImpl implements DeleteChannelUseCase {
    private ChannelRepository channelRepository;

    public DeleteChannelUseCaseImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Completable deleteChannels(List<String> channelsToDeleteList) {
        return channelRepository.deleteChannels(channelsToDeleteList);
    }
}
