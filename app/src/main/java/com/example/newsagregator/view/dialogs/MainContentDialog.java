package com.example.newsagregator.view.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.newsagregator.R;
import com.example.newsagregator.view.MainActivity;

public class MainContentDialog extends DialogFragment {
    WebView mainContentWebView;
    private String guide;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_main_content, container, false);
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainContentWebView = view.findViewById(R.id.newsItemGuide);
        if (getArguments() != null) {
            guide = getArguments().getString(MainActivity.KEY_News_guide);
            mainContentWebView.getSettings().setJavaScriptEnabled(true);
            mainContentWebView.loadUrl(guide);
        }
    }
}
