package com.example.newsagregator;


import android.content.Context;

public class ApplicationContextSingleton {
    private static Context gContext;

    public static void setContext( Context context) {
        gContext = context;
    }

    public static Context getContext() {
        return gContext;
    }
}
