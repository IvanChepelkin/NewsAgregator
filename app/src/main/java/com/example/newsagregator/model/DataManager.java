package com.example.newsagregator.model;

import com.example.newsagregator.model.network.IGetNoticeService;
import com.example.newsagregator.model.network.ServiceApi;
import com.example.newsagregator.model.network.models.NewsRssObject;
import com.example.newsagregator.presenter.INewsData;
import com.example.newsagregator.presenter.model_view.ModelView;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements INewsData, IGetNoticeService.CallBackApi {

    private final String RSS_link = "https://www.sports.ru/rss/rubric.xml?s=208";
    private final String RSS_to_GSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    public List<ModelView> listModelView = new ArrayList<>();
    private FinishedListener finishedListener;

    public DataManager() {
    }

    @Override
    public void onCompleted(NewsRssObject rssObject) {
        setListModelView(rssObject);
        finishedListener.setData(listModelView);
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void getData(FinishedListener finishedListener) {
        this.finishedListener = finishedListener;
        ServiceApi.getServiceApiInstance().setSubcriber(this);
        ServiceApi.getServiceApiInstance().execute(RSS_to_GSON + RSS_link);
    }

    private void setListModelView(NewsRssObject rssObject) {

        for (int i = 0; i < rssObject.getItems().size(); i++) {
            listModelView.add(new ModelView(
                    rssObject.getItems().get(i).getTitle(),
                    rssObject.getItems().get(i).getGuid(),
                    rssObject.getItems().get(i).getContent()));

        }
    }

}
