package com.example.newsagregator.model.domain.Channel.channel_usecase;

import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import java.util.List;
import io.reactivex.Single;

public interface GetChannelUseCase {
    Single<List<ChannelItem>> getChannels();
}
