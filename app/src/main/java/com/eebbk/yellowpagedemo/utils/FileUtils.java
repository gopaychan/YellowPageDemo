package com.eebbk.yellowpagedemo.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.hzy.lib7z.Un7Zip;

import java.io.File;

/**
 * Created by gopaychan on 2017/3/4.
 */

public class FileUtils {
    private FileUtils(){}

    public static boolean isFileExist(@NonNull String path){
        return isFileExist(new File(path));
    }

    public static boolean isFileExist(@NonNull File file){
        if (isExternalStorage()){
            return file.exists();
        }else return false;
    }

    public static boolean isExternalStorage(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getFileAvailableSize(@NonNull String filePath) {
        return getFileAvailableSize( new File(filePath) );
    }

    public static long getFileAvailableSize(@NonNull File file) {
        return file.getUsableSpace();
    }

    public static long getFileSize(@NonNull String filePath){
        return getFileSize(new File(filePath));
    }

    public static long getFileSize(@NonNull File file){
        if (file.exists()){
            return file.length();
        }else{
            return 0;
        }
    }

    public static void deleteFile(@NonNull File file){
        file.delete();
    }

    public static boolean extract7z(String filePath, String outPath) {
        return Un7Zip.extract7z(filePath, outPath);
    }

    public static boolean extractAssets(Context context, String assetPath, String outPath) {
        return Un7Zip.extractAssets(context,assetPath,outPath);
    }
}
