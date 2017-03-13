package com.eebbk.yellowpagedemo.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.eebbk.yellowpagedemo.model.YellowPageModel.YELLOWPAGE_COLLECTION;

/**
 * Created by gopaychan on 2017/3/4.
 */

public class YellowPageContentProvider extends ContentProvider {
    private SQLiteDatabase mDatabase;
    private static UriMatcher mUriMatcher;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(YellowPageModel.AUTHORITY, YellowPageModel.DATABASE_TABLE_NAME, YELLOWPAGE_COLLECTION);
    }

    @Override
    public boolean onCreate() {
//        mDatabase = SQLiteDatabase.openOrCreateDatabase(YellowPageModel.YELLOWPAGE_DATABASE_PATH, null);
        return false;
    }

    @Override
    public Cursor query(@Nullable Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        switch (mUriMatcher.match(uri)) {
            case YellowPageModel.YELLOWPAGE_COLLECTION:
                return mDatabase.query(YellowPageModel.DATABASE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
            default:
                return null;
        }
    }

    @Override
    public String getType(@Nullable Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@Nullable Uri uri, ContentValues contentValues) {
        throw new IllegalArgumentException("no allow insert Values");
    }

    @Override
    public int delete(@Nullable Uri uri, String s, String[] strings) {
        throw new IllegalArgumentException("no allow delete Values");
    }

    @Override
    public int update(@Nullable Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new IllegalArgumentException("no allow update Values");
    }
}
