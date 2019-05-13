package com.example.newsagregator.model.data.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsagregator.model.domain.NewsItem;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsManager";
    private static final String TABLE_CHANNELS = "channels";
    private static final String TABLE_NEWS_ITEMS = "news_items";
    private static final String CHANNEL_URL = "channels_id";
    private static final String ID_CHANNELS = "id_channels";
    private static final String URL = "url";
    private static final String CHANNEL_NAME = "url";
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
                + " FOREIGN KEY (" + CHANNEL_URL + ") REFERENCES " + TABLE_CHANNELS + "(" + URL + "));";
        //+ ")";
        db.execSQL(CREATE_NEWS_ITEMS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS_ITEMS);

        onCreate(db);
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


    public void addChannelInDataBase(final String urlChannel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(URL, urlChannel);
        db.replace(TABLE_CHANNELS, null, values);
        db.close();
    }


    public List<NewsItem> getNewsFromDataBase() {
        List<NewsItem> newsItemList = new ArrayList<NewsItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NewsItem newsItem = new NewsItem(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));

                newsItemList.add(newsItem);
            } while (cursor.moveToNext());
        }
        return newsItemList;
    }
}
