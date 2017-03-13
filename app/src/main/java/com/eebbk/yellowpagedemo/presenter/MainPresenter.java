package com.eebbk.yellowpagedemo.presenter;

import android.util.Log;
import android.widget.CalendarView;

import com.eebbk.yellowpagedemo.R;
import com.eebbk.yellowpagedemo.View.IMainView;
import com.eebbk.yellowpagedemo.model.YellowPage;
import com.eebbk.yellowpagedemo.model.YellowPageModel;
import com.eebbk.yellowpagedemo.utils.FileUtils;

import java.io.File;

/**
 * Created by gopaychan on 2017/3/4.
 */

public class MainPresenter implements IMainPresenter {
    private IMainView mView;
    private YellowPageModel mModel;

    private static final String TAG = "MainPresenter";

    public MainPresenter(IMainView view){
        mView = view;
    }
    @Override
    public void extract7z() {
        mView.showProgressDialog();
        mExtract7zThread.start();
    }

    private Thread mExtract7zThread = new Thread(){
        @Override
        public void run() {
            super.run();
            String databasePath = mView.getContext().getFilesDir().getParent() + File.separator + "databases";
            if (!FileUtils.isFileExist(databasePath + File.separator + YellowPageModel.DATABASE_NAME + ".db")){
                long ExtractDbSize = 42062848;
                if (FileUtils.getFileAvailableSize(mView.getContext().getFilesDir().getParent() + File.separator + "databases") > ExtractDbSize) {
                    boolean isExtractSuccess = FileUtils.extractAssets(mView.getContext(), "yellow_page.7z", databasePath);
                    Log.e(TAG, "isExtractSuccess:" + isExtractSuccess);
                    File databaseFile = new File(databasePath, YellowPageModel.DATABASE_NAME + ".db");
                    if (FileUtils.getFileSize(databaseFile) == ExtractDbSize && isExtractSuccess){
                        mView.setText("EXTRACT_SUCCESS");
                        mModel = new YellowPageModel(mView.getContext());
                    }else {
//                        FileUtils.deleteFile(databaseFile);
                        mView.setText("EXTRACT_FAIL");
                    }
                }else{
                    mView.setText("SIZE_NOT_ENOUGH");
                }
            }else{
                mView.setText("DATABASES_EXIST");
                mModel = new YellowPageModel(mView.getContext());
            }
            mView.dismissProgressDialog();
        }
    };

    @Override
    public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
        if (mView.isProgressDismiss()){
            if (mModel == null){
                mView.showToast(mView.getContext().getString(R.string.db_not_found));
            }else{
                String date = year + "-" + month + "-" + dayOfMonth;
                YellowPage yellowPage = mModel.getYellowPageByDate(date);
                mView.showDetailDialog(yellowPage.toString());
                Log.e(TAG, "onSelectedDayChange: " + yellowPage.toString() );
            }
        }else {
            mView.showToast(mView.getContext().getString(R.string.loading_db));
        }
    }
}
