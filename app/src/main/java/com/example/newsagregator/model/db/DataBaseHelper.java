package com.example.newsagregator.model.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsagregator.presenter.model_view.ModelView;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsManager@";
    private static final String TABLE_NEWS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_GUIDE = "guide";
    private static final String KEY_CONTENT = "content";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_GUIDE + " TEXT,"
                + KEY_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);

        onCreate(db);
    }

    public void addContact(List<ModelView> modelViewList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < modelViewList.size(); i++) {
            values.put(KEY_TITLE, modelViewList.get(i).getTitle());
            values.put(KEY_GUIDE, modelViewList.get(i).getGuide());
            values.put(KEY_CONTENT, modelViewList.get(i).getContent());
        }

        db.insert(TABLE_NEWS, null, values);
        db.close();
    }

    public List<ModelView> getAllmodelView() {
        List<ModelView> modelViewList = new ArrayList<ModelView>();
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelView modelView = new ModelView(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));

                modelViewList.add(modelView);
            } while (cursor.moveToNext());
        }

        return modelViewList;
    }

}
