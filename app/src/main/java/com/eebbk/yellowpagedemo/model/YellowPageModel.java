package com.eebbk.yellowpagedemo.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.io.File;

/**
 * Created by gopaychan on 2017/3/4.
 */

public class YellowPageModel {
    public static final String DATABASE_NAME = "yellow_page";
    static final String DATABASE_TABLE_NAME = "yellowPage";
    static final int YELLOWPAGE_COLLECTION = 1;
    static final String AUTHORITY = "com.eebbk.yellowpagedemo.provider";
    private static final Uri YELLOWPAGE_URI = Uri.parse("com.eebbk.yellowpagedemo.provider/yellowPage");

    private static class YellowPageColumns {
        static final String STATUS = "status";
        static final String PENGZU = "pengzu";
        static final String DESC = "desc";
        static final String WUXING = "wuxing";
        static final String XINGXIU = "xingxiu";
        static final String CONG = "cong";
        static final String NONGLI = "nongli";
        static final String DATE = "date";
        static final String TGDZ = "tgdz";
        static final String XINGQI = "xingqi";
        static final String SHICHEN = "shichen";
        static final String JI_NEW = "ji_new";
        static final String JI_OLD = "ji_old";
        static final String JIERI = "jieri";
        static final String JIEQI = "jieqi";
        static final String TAISHEN = "taishen";
        static final String FANWEI = "fanwei";
        static final String YI_NEW = "yi_new";
        static final String YI_OLD = "yi_old";
    }

    private SQLiteDatabase mDatabase;
    public YellowPageModel(Context context){
        mDatabase = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().getParent() + File.separator + "databases" + File.separator + DATABASE_NAME + ".db" , null);
    }

    /**
     * @param date    "yyyy-dd-mm"的日期形式
     * @return
     */
    public YellowPage getYellowPageByDate(String date) {
//        Cursor cursor = context.getApplicationContext().getContentResolver().query(YELLOWPAGE_URI, null, "date == ?", new String[]{date}, null);
        YellowPage yellowPage = null;
        Cursor cursor = mDatabase.query(YellowPageModel.DATABASE_TABLE_NAME, null, "date == ?", new String[]{date}, null, null, null);
        if (cursor != null) {
            yellowPage = new YellowPage();
            while (cursor.moveToNext()) {
                yellowPage.desc = cursor.getString(cursor.getColumnIndex(YellowPageColumns.DESC));
                yellowPage.status = cursor.getString(cursor.getColumnIndex(YellowPageColumns.STATUS));
                yellowPage.pengzu = cursor.getString(cursor.getColumnIndex(YellowPageColumns.PENGZU));
                yellowPage.wuxing = cursor.getString(cursor.getColumnIndex(YellowPageColumns.WUXING));
                yellowPage.xingxiu = cursor.getString(cursor.getColumnIndex(YellowPageColumns.XINGXIU));
                yellowPage.cong = cursor.getString(cursor.getColumnIndex(YellowPageColumns.CONG));
                yellowPage.nongli = cursor.getString(cursor.getColumnIndex(YellowPageColumns.NONGLI));
                yellowPage.date = cursor.getString(cursor.getColumnIndex(YellowPageColumns.DATE));
                yellowPage.tgdz = cursor.getString(cursor.getColumnIndex(YellowPageColumns.TGDZ));
                yellowPage.xingqi = cursor.getString(cursor.getColumnIndex(YellowPageColumns.XINGQI));
                yellowPage.shichen = cursor.getString(cursor.getColumnIndex(YellowPageColumns.SHICHEN));
                yellowPage.ji_new = cursor.getString(cursor.getColumnIndex(YellowPageColumns.JI_NEW));
                yellowPage.ji_old = cursor.getString(cursor.getColumnIndex(YellowPageColumns.JI_OLD));
                yellowPage.jieri = cursor.getString(cursor.getColumnIndex(YellowPageColumns.JIERI));
                yellowPage.jieqi = cursor.getString(cursor.getColumnIndex(YellowPageColumns.JIEQI));
                yellowPage.taishen = cursor.getString(cursor.getColumnIndex(YellowPageColumns.TAISHEN));
                yellowPage.fanwei = cursor.getString(cursor.getColumnIndex(YellowPageColumns.FANWEI));
                yellowPage.yi_new = cursor.getString(cursor.getColumnIndex(YellowPageColumns.YI_NEW));
                yellowPage.yi_old = cursor.getString(cursor.getColumnIndex(YellowPageColumns.YI_OLD));
            }
            cursor.close();
        }
        return yellowPage;
    }
}
