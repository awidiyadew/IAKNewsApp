package id.iak.iaknewsapp.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.iak.iaknewsapp.local.DbContract.NewsItemContract;
import id.iak.iaknewsapp.model.ArticlesItem;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "IAKNews";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NEWS =
            "CREATE TABLE " + NewsItemContract.TABLE_NAME + " (" +
                    NewsItemContract._ID + " INTEGER PRIMARY KEY, " +
                    NewsItemContract.URL + " TEXT, " +
                    NewsItemContract.TITLE + " TEXT," +
                    NewsItemContract.DESC + " TEXT," +
                    NewsItemContract.IMG_URL + " TEXT," +
                    NewsItemContract.PUBLISH_AT + " TEXT," +
                    NewsItemContract.AUTHOR + " TEXT" +
                    "); ";

    private static final String SQL_DROP_TABLE_NEWS  =
            "DROP TABLE IF EXISTS " + NewsItemContract.TABLE_NAME;

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

    public long saveNewsItem(ArticlesItem newsItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NewsItemContract.URL, newsItem.getUrl());
        cv.put(NewsItemContract.TITLE, newsItem.getTitle());
        cv.put(NewsItemContract.DESC, newsItem.getDescription());
        cv.put(NewsItemContract.IMG_URL, newsItem.getUrlToImage());
        cv.put(NewsItemContract.AUTHOR, newsItem.getAuthor());
        cv.put(NewsItemContract.PUBLISH_AT, newsItem.getPublishedAt());

        return db.insert(NewsItemContract.TABLE_NAME, null, cv);
    }

}
