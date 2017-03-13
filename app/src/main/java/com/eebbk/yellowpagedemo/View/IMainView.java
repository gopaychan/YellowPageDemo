package com.eebbk.yellowpagedemo.View;

import android.content.Context;

/**
 * Created by gopaychan on 2017/3/4.
 */

public interface IMainView {
    void showToast(String msg);
    void showProgressDialog();
    void dismissProgressDialog();
    void showDetailDialog(String msg);
    Context getContext();
    void setText(String msg);
    boolean isProgressDismiss();
}
