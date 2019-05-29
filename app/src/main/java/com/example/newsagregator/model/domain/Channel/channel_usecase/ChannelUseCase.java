package com.example.newsagregator.model.domain.Channel.channel_usecase;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import java.util.List;
import io.reactivex.Single;

public interface ChannelUseCase {
    Single<List<ChannelItem>> getChannels();
}
