package id.iak.iaknewsapp.local;

import android.provider.BaseColumns;

public class DbContract {
    public static class NewsItemContract implements BaseColumns {

        public static final String TABLE_NAME = "news";

        public static final String URL = "url";
        public static final String TITLE = "title";
        public static final String DESC = "desc";
        public static final String IMG_URL = "img_url";
        public static final String AUTHOR = "author";
        public static final String PUBLISH_AT = "publish_at";
    }
}
