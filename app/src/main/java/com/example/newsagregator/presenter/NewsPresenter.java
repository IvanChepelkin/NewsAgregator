package com.example.newsagregator.presenter;

import android.util.Log;

import com.example.newsagregator.model.domain.News.NewsPresenterListener;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import com.example.newsagregator.model.domain.Channel.channel_usecase.ChannelUseCase;
import com.example.newsagregator.model.domain.News.news_usecase.NewsUseCase;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;
import com.example.newsagregator.model.domain.News.SubscribeUseCaseNews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private List<NewsItem> listNewsItemSort;
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
                                    newsView.clearList();
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
                        Log.d("Ошибка удаления", "error: " + e);
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

        if (channelItemList != null && channelItemList.size() > 0) {

            for (ChannelItem channel : channelItemList) {
                if (channel.getChannelUrl().equals(channeSavelUrl)) {
                    newsView.showIsChannelToast();
                    break;
                }
            }
            loadChannels();
        } else {
            loadChannels();
        }
    }

    public void setClickOkDeleteChannels(final boolean[] positionChannelToDeleteArray) {
        if (positionChannelToDeleteArray != null) {
            List<String> channelsToDeleteList = new ArrayList<>();

            for (int i = 0; i < positionChannelToDeleteArray.length; i++) {
                if (positionChannelToDeleteArray[i]) {
                    channelsToDeleteList.add(channelItemList.get(i).getChannelUrl());
                }
            }
            deleteChannels(channelsToDeleteList);
        } else {
            newsView.showNotCahnnelToast();
        }
    }


    public void setClickItemNews(int position) {
        String guid = listNewsItemSort.get(position).getGuide();
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
    public void setNewsItemList(List<NewsItem> listNewsItem) {
        channeSavelUrl = null;
        this.listNewsItemSort = sortNewsByDate(listNewsItem);
        Collections.reverse(listNewsItemSort);
        newsView.hideProgress();
        newsView.showNews(listNewsItemSort);

    }

    private List<NewsItem> sortNewsByDate(List<NewsItem> listNewsItem) {
        Map<Date, NewsItem> mapNewsItem = new TreeMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (NewsItem newsItem : listNewsItem) {
            try {
                Date date = simpleDateFormat.parse(newsItem.getDatePublication());
                mapNewsItem.put(date, newsItem);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<NewsItem> sortNewsItemlist = new ArrayList<>();
        for (Date date : mapNewsItem.keySet()) {
            sortNewsItemlist.add(mapNewsItem.get(date));
        }
        return sortNewsItemlist;
    }

    @Override
    public void setError(Throwable exeption) {
        channeSavelUrl = null;
        newsView.showErrorToast();
        loadChannels();
    }

}