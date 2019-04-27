package com.example.newsagregator.model.data;

import com.example.newsagregator.model.domain.NewsEmptity;
import java.util.List;

public interface RepoNews {
    void getData(CallBacRepo callBackApi);

    interface CallBacRepo {

        void setData(List<NewsEmptity> listNewsEmptity);

    }
}