package com.example.newsagregator.presenter;

import com.example.newsagregator.model.domain.News.NewsPresenterListener;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import com.example.newsagregator.model.domain.Channel.channel_usecase.ChannelUseCase;
import com.example.newsagregator.model.domain.News.news_usecase.NewsUseCase;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;
import com.example.newsagregator.model.domain.News.SubscribeUseCaseNews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NewsPresenter implements NewsPresenterListener {
    private NewsView newsView;
    private NewsUseCase newsUseCase;
    private ChannelUseCase channelUseCase;
    private List<NewsItem> listNewsItem;
    private List<ChannelItem> channelItemList;
    private String[] channelsArray;
    private String channeSavelUrl;
    private Disposable disposable;
    private CompositeDisposable instance;

    public NewsPresenter(NewsUseCase newsUseCase,
                         ChannelUseCase channelUseCase,
                         SubscribeUseCaseNews subscribeUseCaseNews) {

        this.newsUseCase = newsUseCase;
        this.channelUseCase = channelUseCase;
        subscribeUseCaseNews.subscribePresenterNews(this);
    }

    public void onAttachView(NewsView newsView) {
        this.newsView = newsView;
        updateNews();
    }

    public void detachView() {
        this.newsView = null;
    }

    public void updateNews() {
        loadChannels();
    }

    private void loadChannels() {

        instance = new CompositeDisposable();
        newsView.showProgress();
        Single<List<ChannelItem>> responce = channelUseCase.getChannels();

        disposable = responce
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<ChannelItem>>() {
                            @Override
                            public void accept(List<ChannelItem> responseSuccess) {
                                channelItemList = responseSuccess;

                                if (channelItemList.size() == 0 && channeSavelUrl == null) {
                                    newsView.showNotCahnnelToast();
                                    newsView.hideProgress();
                                } else if (channelItemList.size() == 0) {
                                    loadNews(channelItemList);
                                } else {
                                    setChannelsArray(channelItemList);
                                    loadNews(channelItemList);
                                }

                                System.out.println("Работатет!");
                            }
                        }
                );
        instance.add(disposable);
    }

    private void deleteChannels(List<String> channelsToDeleteList) {
        //channelUseCase.deleteChannels(channelsToDeleteList);
        Completable responce = channelUseCase.deleteChannels(channelsToDeleteList);
        responce
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        loadChannels();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public void setClickAddChannel() {
        newsView.showAlertDialogAddChannel();
    }

    public void setClickDeleteChannel() {
        newsView.showAlertDialogDeleteChannel(channelsArray);
    }

    public void setClickOkAddChannels(final String channeSavelUrl) {

        this.channeSavelUrl = channeSavelUrl;
        loadChannels();
    }

    public void setClickOkDeleteChannels(final boolean[] positionChannelToDeleteArray) {
        List<String> channelsToDeleteList = new ArrayList<>();

        for (int i = 0; i < positionChannelToDeleteArray.length; i++) {
            if (positionChannelToDeleteArray[i]) {
                channelsToDeleteList.add(channelItemList.get(i).getChannelUrl());
            }
        }
        deleteChannels(channelsToDeleteList);
    }


    public void setClickItemNews(int position) {
        String guid = listNewsItem.get(position).getGuide();
        newsView.showMainConent(guid);
    }

    private void setChannelsArray(List<ChannelItem> channelItemListList) {
        List<String> channelList = new ArrayList<>();

        for (int i = 0; i < channelItemListList.size(); i++) {
            channelList.add(channelItemListList.get(i).getChannelName());
        }
        channelsArray = channelList.toArray(new String[0]);
    }

    private void loadNews(final List<ChannelItem> channelItemListList) {

        List<String> channelList = new ArrayList<>();

        for (int i = 0; i < channelItemListList.size(); i++) {
            channelList.add(channelItemListList.get(i).getChannelUrl());
        }

        if (channeSavelUrl != null) {
            channelList.add(channeSavelUrl);
            newsUseCase.getNews(channelList);

        } else {
            newsUseCase.getNews(channelList);
        }
    }

    @Override
    public void setNewsItemList(final List<NewsItem> listNewsItem) {
        channeSavelUrl = null;
        this.listNewsItem = listNewsItem;
        Collections.reverse(listNewsItem);
        newsView.hideProgress();
        newsView.showNews(listNewsItem);
    }

    @Override
    public void setError(Throwable exeption) {
        newsView.showErrorToast();
    }

//    @Override
//    public void setChannelsItemList(List<ChannelItem> channelItemList) {
//
//        if (channelItemList.size() == 0 && channeSavelUrl == null) {
//            newsView.showNotCahnnelToast();
//            newsView.hideProgress();
//        } else if (channelItemList.size() == 0) {
//            loadNews(channelItemList);
//        } else {
//            this.channelItemList = channelItemList;
//            setChannelsArray(channelItemList);
//            loadNews(channelItemList);
//        }
//    }
//
//    @Override
//    public void ChannelsDeleteCompleted(Boolean onFinishDeleteChannels) {
//        if (onFinishDeleteChannels) {
//            loadChannels();
//        }
//    }
}