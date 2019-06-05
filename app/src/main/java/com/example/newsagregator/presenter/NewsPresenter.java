package com.example.newsagregator.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.newsagregator.model.domain.Channel.channel_delete_usecase.DeleteChannelUseCase;
import com.example.newsagregator.model.domain.Channel.channel_entity.ChannelItem;
import com.example.newsagregator.model.domain.Channel.channel_save_usecase.SaveChannelUseCase;
import com.example.newsagregator.model.domain.Channel.channel_usecase.GetChannelUseCase;
import com.example.newsagregator.model.domain.News.news_usecase.GetNewsUseCase;
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
    private GetNewsUseCase getNewsUseCase;
    private GetChannelUseCase getChannelUseCase;
    private SaveChannelUseCase saveChannelUseCase;

    private DeleteChannelUseCase deleteChannelUseCase;
    private List<NewsItem> listNewsItemSort;
    private List<ChannelItem> channeslItemList;
    private String[] channelsArray;
    private CompositeDisposable disposables = new CompositeDisposable();

    public NewsPresenter(GetNewsUseCase getNewsUseCase,
                         GetChannelUseCase getChannelUseCase,
                         DeleteChannelUseCase deleteChannelUseCase,
                         SaveChannelUseCase saveChannelUseCase) {

        this.getNewsUseCase = getNewsUseCase;
        this.getChannelUseCase = getChannelUseCase;
        this.deleteChannelUseCase = deleteChannelUseCase;
        this.saveChannelUseCase = saveChannelUseCase;
    }

    public void onAttachView(NewsView newsView) {
        this.newsView = newsView;
        updateNews();
    }

    public void detachView() {
        if(disposables.isDisposed()){
            disposables.dispose();
        }
        this.newsView = null;
    }

    public void updateNews() {
        loadChannels();
    }

    private void loadChannels() {

        newsView.showProgress();

        Single<List<ChannelItem>> responce = getChannelUseCase.getChannels();
        responce
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ChannelItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(List<ChannelItem> channelItemList) {
                        channeslItemList = channelItemList;
                        if (channelItemList.size() == 0) {
                            newsView.showErrorNotCahnnelToast();
                            newsView.clearList();
                            newsView.hideProgress();
                        } else {
                            setChannelsArray(channelItemList);
                            loadNews(channelItemList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsView.hideProgress();
                        newsView.showErrorToast();
                    }
                });
    }

    private void deleteChannels(List<String> channelsToDeleteList) {

        Completable responce = deleteChannelUseCase.deleteChannels(channelsToDeleteList);
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
                        newsView.showErrorChannelTodelete();
                    }
                });
    }

    private void saveChannel(final String channeSavelUrl) {
        newsView.showProgress();
        Single<ChannelItem> channelSaveResponce = saveChannelUseCase.saveChannel(channeSavelUrl);
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

        Single<List<NewsItem>> responce = getNewsUseCase.getNews(channelList);
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
                        newsView.hideProgress();
                        newsView.showErrorToast();
                    }
                });
    }

    public void setClickAddChannel() {
        newsView.showAlertDialogAddChannel();
    }

    public void setClickOkAddChannels(final String channeSavelUrl) {

        boolean isChannel = false;
        if (channeslItemList != null && channeslItemList.size() != 0) {
            for (ChannelItem channel : channeslItemList) {
                if (channel.getChannelUrl().equals(channeSavelUrl)) {
                    newsView.showErrorIsChannelToast();
                    isChannel = true;
                    break;
                }
            }
        }
        if (!isChannel) {
            saveChannel(channeSavelUrl);
        }
    }

    public void setClickDeleteChannel() {
        newsView.showAlertDialogDeleteChannel(channelsArray);
    }

    public void setClickOkDeleteChannels(final boolean[] positionChannelToDeleteArray) {

        if (positionChannelToDeleteArray != null) {
            List<String> channelsToDeleteList = new ArrayList<>();

            for (int i = 0; i < positionChannelToDeleteArray.length; i++) {
                if (positionChannelToDeleteArray[i]) {
                    channelsToDeleteList.add(channeslItemList.get(i).getChannelUrl());
                }
            }
            deleteChannels(channelsToDeleteList);
        } else {
            newsView.showErrorNotCahnnelToast();
        }
    }

    public void setClickItemNews(int position) {
        String guide = listNewsItemSort.get(position).getGuide();
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