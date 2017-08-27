package id.iak.iaknewsapp.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.iak.iaknewsapp.local.DbContract.NewsItemContract;
import id.iak.iaknewsapp.model.ArticlesItem;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = DbOpenHelper.class.getSimpleName();
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

        long rowId = db.insert(NewsItemContract.TABLE_NAME, null, cv);
        db.close();

        return rowId;
    }

    public boolean deleteNewsItem(String newsUrl){
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = NewsItemContract.URL + "=?";
        String[] whereArgs = new String[]{ newsUrl };
        int rowEffected = db.delete(NewsItemContract.TABLE_NAME, whereClause, whereArgs);

        db.close();

        return rowEffected > 0;
    }

    public boolean isNewsSavedAsFavorite(String newsUrl){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                NewsItemContract.TABLE_NAME,
                null,
                NewsItemContract.URL + "=?",
                new String[]{ newsUrl },
                null,
                null,
                null
        );

        int totalRow = cursor.getCount();
        db.close();
        return totalRow > 0;
    }

    public List<ArticlesItem> getFavoriteNews(){
        SQLiteDatabase db = this.getReadableDatabase();

        /* select * from table*/
        Cursor cursor = db.query(
                NewsItemContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        int resultCount = cursor.getCount();
        Log.d(TAG, "getFavoriteNews: ");

        List<ArticlesItem> articlesItems = new ArrayList<>();
        if (resultCount > 0){
            cursor.moveToFirst();
            do {
                String url = cursor.getString(cursor.getColumnIndex(NewsItemContract.URL));
                String title = cursor.getString(cursor.getColumnIndex(NewsItemContract.TITLE));
                String desc = cursor.getString(cursor.getColumnIndex(NewsItemContract.DESC));
                String img_url = cursor.getString(cursor.getColumnIndex(NewsItemContract.IMG_URL));
                String author = cursor.getString(cursor.getColumnIndex(NewsItemContract.AUTHOR));
                String published_at = cursor.getString(cursor.getColumnIndex(NewsItemContract.PUBLISH_AT));

                ArticlesItem item = new ArticlesItem();
                item.setUrl(url);
                item.setTitle(title);
                item.setDescription(desc);
                item.setUrlToImage(img_url);
                item.setAuthor(author);
                item.setPublishedAt(published_at);

                articlesItems.add(item);
                Log.d(TAG, "getFavoriteNews: news " + item.getTitle());
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return articlesItems;
    }

}
