package com.example.newsagregator.model.domain;

import com.example.newsagregator.model.domain.CallbacksInterfaces.NewsPresenterListener;

public interface SubscribeUseCase {

    void subscribePresenter(NewsPresenterListener newsPresenterListener);

}
