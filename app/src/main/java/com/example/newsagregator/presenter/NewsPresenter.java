package com.example.newsagregator.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.newsagregator.model.domain.Channel.channel_delete_usecase.ChannelDeleteUseCase;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import com.example.newsagregator.model.domain.Channel.channel_save_usecase.ChannelSaveUseCase;
import com.example.newsagregator.model.domain.Channel.channel_usecase.ChannelUseCase;
import com.example.newsagregator.model.domain.News.news_usecase.NewsUseCase;
import com.example.newsagregator.model.domain.News.news_entity.NewsItem;

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
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class NewsPresenter {
    private NewsView newsView;
    private NewsUseCase newsUseCase;
    private ChannelUseCase channelUseCase;
    private ChannelSaveUseCase channelSaveUseCase;

    private ChannelDeleteUseCase channelDeleteUseCase;
    private List<NewsItem> listNewsItemSort;
    private List<ChannelItem> channelItemList;
    private String[] channelsArray;
    private CompositeDisposable disposables = new CompositeDisposable();

    public NewsPresenter(NewsUseCase newsUseCase,
                         ChannelUseCase channelUseCase,
                         ChannelDeleteUseCase channelDeleteUseCase,
                         ChannelSaveUseCase channelSaveUseCase) {

        this.newsUseCase = newsUseCase;
        this.channelUseCase = channelUseCase;
        this.channelDeleteUseCase = channelDeleteUseCase;
        this.channelSaveUseCase = channelSaveUseCase;
    }

    public void onAttachView(NewsView newsView) {
        this.newsView = newsView;
        updateNews();
    }

    public void detachView() {
        disposables.clear();
        this.newsView = null;
    }

    public void updateNews() {
        loadChannels();
    }

    private void loadChannels() {

        newsView.showProgress();

        Single<List<ChannelItem>> responce = channelUseCase.getChannels();

        Disposable disposChannel = responce
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseSuccess -> {
                            channelItemList = responseSuccess;

                            if (channelItemList.size() == 0) {
                                newsView.showErrorNotCahnnelToast();
                                newsView.clearList();
                                newsView.hideProgress();
                            } else {
                                setChannelsArray(channelItemList);
                                loadNews(channelItemList);
                            }
                        }
                );
        disposables.add(disposChannel);
    }

    private void deleteChannels(List<String> channelsToDeleteList) {

        Completable responce = channelDeleteUseCase.deleteChannels(channelsToDeleteList);
        responce
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
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

    private void saveChannel(final String channeSavelUrl) {
        newsView.showProgress();
        Single<ChannelItem> channelSaveResponce = channelSaveUseCase.saveChannels(channeSavelUrl);
        channelSaveResponce
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ChannelItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(ChannelItem channelItem) {
                        loadChannels();
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsView.hideProgress();
                        newsView.showErrorInvalidAddress();
                    }
                });
    }

    private void loadNews(final List<ChannelItem> channelItemList) {

        List<String> channelList = new ArrayList<>();

        for (int i = 0; i < channelItemList.size(); i++) {
            channelList.add(channelItemList.get(i).getChannelUrl());
        }

        Single<List<NewsItem>> responce = newsUseCase.getNews(channelList);

        responce
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<NewsItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(List<NewsItem> listNewsItem) {
                        listNewsItemSort = sortNewsByDate(listNewsItem);
                        Collections.reverse(listNewsItemSort);
                        newsView.hideProgress();
                        newsView.showNews(listNewsItemSort);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        newsView.hideProgress();
                        newsView.showErrorToast();
                    }
                });
    }

    public void setClickAddChannel() {
        newsView.showAlertDialogAddChannel();
    }

    public void setClickOkAddChannels(final String channeSavelUrl) {
        saveChannel(channeSavelUrl);
    }

    public void setClickDeleteChannel() {
        newsView.showAlertDialogDeleteChannel(channelsArray);
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
            newsView.showErrorNotCahnnelToast();
        }
    }

    public void setClickItemNews(int position) {
        String guide= listNewsItemSort.get(position).getGuide();
        newsView.showMainConent(guide);
    }


    public void setClickSendGuideButton(int position) {
        String guide = listNewsItemSort.get(position).getGuide();
        newsView.sendGuide(guide);
    }


    private void setChannelsArray(List<ChannelItem> channelItemListList) {
        List<String> channelList = new ArrayList<>();

        for (int i = 0; i < channelItemListList.size(); i++) {
            channelList.add(channelItemListList.get(i).getChannelName());
        }
        channelsArray = channelList.toArray(new String[0]);
    }

    private List<NewsItem> sortNewsByDate(List<NewsItem> listNewsItem) {
        Map<Date, NewsItem> mapNewsItem = new TreeMap<>();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (NewsItem newsItem : listNewsItem) {
            try {
                Date date = simpleDateFormat.parse(newsItem.getDatePublication());
                mapNewsItem.put(date, newsItem);
            } catch (ParseException e) {
                Log.d("Неверный формат даты ", "error: " + e);
            }
        }
        List<NewsItem> sortNewsItemlist = new ArrayList<>();
        for (Date date : mapNewsItem.keySet()) {
            sortNewsItemlist.add(mapNewsItem.get(date));
        }
        return sortNewsItemlist;
    }
}