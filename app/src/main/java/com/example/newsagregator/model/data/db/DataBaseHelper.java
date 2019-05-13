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
    private static final String TABLE_NEWS = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_GUIDE = "guide";
    private static final String KEY_CONTENT = "content";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NEWS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_GUIDE + " TEXT,"
                + KEY_CONTENT + " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);

        onCreate(db);
    }


    public void addNewsInDataBase(List<NewsItem> newsItemList) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < newsItemList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, newsItemList.get(i).getTitle());
            values.put(KEY_GUIDE, newsItemList.get(i).getGuide());
            values.put(KEY_CONTENT, newsItemList.get(i).getContent());
            db.insert(TABLE_NEWS, null, values);
        }
        db.close();
    }

    public List<NewsItem> getNewsFromDataBase() {
        List<NewsItem> newsItemList = new ArrayList<NewsItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS;

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
