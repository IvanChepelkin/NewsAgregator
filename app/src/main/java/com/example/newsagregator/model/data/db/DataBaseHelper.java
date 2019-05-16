package com.example.newsagregator.model.data.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.newsagregator.model.domain.Channel.ChannelItem;
import com.example.newsagregator.model.domain.News.NewsItem;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsManager";
    private static final String TABLE_CHANNELS = "channels";
    private static final String TABLE_NEWS_ITEMS = "news_items";
    private static final String CHANNEL_URL = "channels_url";
    private static final String URL = "url";
    private static final String CHANNEL_NAME = "channel_name";
    private static final String ID_NEWS_ITEMS = "id_channels";
    private static final String TITLE = "title";
    private static final String GUIDE = "guide";
    private static final String CONTENT = "content";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CHANNELS_TABLE = "CREATE TABLE " + TABLE_CHANNELS + " ("
                + URL + " TEXT PRIMARY KEY,"
                + CHANNEL_NAME + " TEXT " + ")";
        db.execSQL(CREATE_CHANNELS_TABLE);


        String CREATE_NEWS_ITEMS_TABLE = "CREATE TABLE " + TABLE_NEWS_ITEMS + " ("
                + ID_NEWS_ITEMS + " INTEGER PRIMARY KEY,"
                + TITLE + " TEXT,"
                + GUIDE + " TEXT,"
                + CONTENT + " TEXT,"
                + CHANNEL_URL + " TEXT,"
                + " FOREIGN KEY (" + CHANNEL_URL + ") REFERENCES " + TABLE_CHANNELS + "(" + URL + ") ON UPDATE CASCADE ON DELETE CASCADE );";
        db.execSQL(CREATE_NEWS_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS_ITEMS);

        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        db.execSQL("PRAGMA foreign_keys=ON;");
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void addNewsInDataBase(List<NewsItem> newsItemList, String urlChannel) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < newsItemList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(TITLE, newsItemList.get(i).getTitle());
            values.put(GUIDE, newsItemList.get(i).getGuide());
            values.put(CONTENT, newsItemList.get(i).getContent());
            values.put(CHANNEL_URL, urlChannel);
            db.insert(TABLE_NEWS_ITEMS, null, values);
        }
        db.close();
    }

    public void addChannelInDataBase(ChannelItem channelItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(URL, channelItem.getChannelUrl());
        values.put(CHANNEL_NAME, channelItem.getChannelName());

        int id = getID(channelItem.getChannelUrl());
        if (id == -1)
            db.insert(TABLE_CHANNELS, null, values);
//        else
//            db.update(TABLE_CHANNELS, values, URL + "=?", new String[]{Integer.toString(id)});

        db.close();

    }

    private int getID(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_CHANNELS,
                new String[]{URL},
                "url =? ",
                new String[]{url},
                null,
                null,
                null,
                null);
        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex(URL));
        return -1;
    }


    public List<NewsItem> getNewsFromDataBase() {
        List<NewsItem> newsItemList = new ArrayList<NewsItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS_ITEMS;

        System.out.println("МЕТОД ВЫЗЫВАЕТСЯ");

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NewsItem newsItem = new NewsItem(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));

//                Log.d("mLog", "ID = " + cursor.getString(1) +
//                        ", name = " + cursor.getString(2) +
//                        ", email = " + cursor.getString(3));

                newsItemList.add(newsItem);
            } while (cursor.moveToNext());
        }
        return newsItemList;
    }

    public List<ChannelItem> getChannelsFromDataBase() {
        List<ChannelItem> channelItemList = new ArrayList<ChannelItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHANNELS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ChannelItem channelItem = new ChannelItem(
                        cursor.getString(1),
                        cursor.getString(0));

                Log.d("mLog", "ID = " + cursor.getString(0) +
                        ", name = " + cursor.getString(1));

                channelItemList.add(channelItem);
            } while (cursor.moveToNext());
        }
        return channelItemList;
    }

    public boolean deleteChannels(List<String> channelsToDeleteList) {
        boolean onFinishDeleteChannels;
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < channelsToDeleteList.size(); i++) {

            int del = db.delete(TABLE_CHANNELS, URL + "= ?", new String[]{channelsToDeleteList.get(i)});

            System.out.println("Удалил");

            // Log.d("mLog", "deleted rows count = " + del);
            Log.d("mLog", "deleted rows count = " + del);

        }
        onFinishDeleteChannels = true;
        db.close();
        return onFinishDeleteChannels;
    }
}
