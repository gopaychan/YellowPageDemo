package com.eebbk.yellowpagedemo.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.eebbk.yellowpagedemo.R;
import com.eebbk.yellowpagedemo.View.IMainView;
import com.eebbk.yellowpagedemo.presenter.IMainPresenter;
import com.eebbk.yellowpagedemo.presenter.MainPresenter;
import com.eebbk.yellowpagedemo.utils.ThreadUtils;
import com.eebbk.yellowpagedemo.utils.ToastUtils;

public class MainActivity extends BaseActivity implements IMainView {

    private static final String TAG = "MainActivity";
    private TextView mExtractResultTv;
    private IMainPresenter mPresenter;
    private ProgressDialog mPd;
    private CalendarView mCalendarView;
    private Dialog mShowDetailDl;
    private TextView mDetailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPresenter = new MainPresenter(this);
        mPresenter.extract7z();
    }

    private void initView() {
        mExtractResultTv = findView(R.id.extractResultTxtId);
        mPd = new ProgressDialog(this);
        mCalendarView = findView(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                mPresenter.onSelectedDayChange(calendarView,year,month + 1,dayOfMonth);
            }
        });
        View v =  LayoutInflater.from(this).inflate(R.layout.dialog_yellow_page_detail_layout,null);
        mDetailTv = (TextView) v.findViewById(R.id.yellowPageDetailTvID);

        mShowDetailDl = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.yellow_page))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(R.string.certain, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setView(v)
                .create();

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.getInstance(this).s(msg);
    }

    @Override
    public void showProgressDialog() {
        if (mPd != null && !mPd.isShowing()) {
            if (ThreadUtils.isMainThread())
                mPd.show();
            else {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mPd.show();
                    }
                });
            }
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mPd != null && mPd.isShowing())
            if (ThreadUtils.isMainThread())
                mPd.dismiss();
            else {
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mPd.dismiss();
                    }
                });
            }
    }

    @Override
    public void showDetailDialog(final String msg) {
        if (ThreadUtils.isMainThread()) {
            mDetailTv.setText(msg);
            mShowDetailDl.show();
        }
        else {
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mDetailTv.setText(msg);
                    mShowDetailDl.show();
                }
            });
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setText(final String msg) {
        if (ThreadUtils.isMainThread())
            mExtractResultTv.setText(msg);
        else {
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mExtractResultTv.setText(msg);
                }
            });
        }
    }

    @Override
    public boolean isProgressDismiss() {
        if (mPd == null || mPd.isShowing()) return false;
        else return true;
    }
}
