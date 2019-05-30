package com.example.newsagregator.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.newsagregator.R;

public class MainContentFragment extends Fragment {

    private WebView webViewContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_main_content, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        String guide = getArguments().getString(MainActivity.KEY_News_guide);
        setContent(guide);

    }

    private void initViews(View view) {
        webViewContent = view.findViewById(R.id.newsItemGuide);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setContent(String guid) {

        webViewContent.getSettings().setJavaScriptEnabled(true);
        webViewContent.loadUrl(guid);
    }
}
