package com.eebbk.yellowpagedemo.ui;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by gopaychan on 2017/3/4.
 */

public class BaseActivity extends AppCompatActivity {

    public <T extends View> T findView(@IdRes int id){
        return (T)findViewById(id);
    }
}
