package id.iak.iaknewsapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import id.iak.iaknewsapp.model.ArticlesItem;

import static id.iak.iaknewsapp.database.DbContract.NewsContract;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iaknews";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NEWS =
            "CREATE TABLE " + NewsContract.TABLE_NAME + " (" +
                    NewsContract._ID + " INTEGER PRIMARY KEY, " +
                    NewsContract.URL + " TEXT, " +
                    NewsContract.TITLE + " TEXT," +
                    NewsContract.DESC + " TEXT," +
                    NewsContract.IMG_URL + " TEXT," +
                    NewsContract.PUBLISHED_AT + " TEXT," +
                    NewsContract.AUTHOR + " TEXT" +
                    "); ";

    private static final String SQL_DROP_TABLE_NEWS  =
            "DROP TABLE IF EXISTS " + NewsContract.TABLE_NAME;
    
    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_NEWS);
        onCreate(db);
    }

    public boolean saveNewsItem(ArticlesItem newsItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NewsContract.URL, newsItem.getUrl());
        cv.put(NewsContract.TITLE, newsItem.getTitle());
        cv.put(NewsContract.DESC, newsItem.getDescription());
        cv.put(NewsContract.IMG_URL, newsItem.getUrlToImage());
        cv.put(NewsContract.AUTHOR, newsItem.getAuthor());
        cv.put(NewsContract.PUBLISHED_AT, newsItem.getPublishedAt());

        long rowId = db.insert(NewsContract.TABLE_NAME, null, cv);
        db.close();

        Log.d("OpenHelper", "isSaveSuccess ? " + String.valueOf(rowId > 0));

       return rowId > 0;
    }



}
