package com.eebbk.yellowpagedemo.presenter;

import android.widget.CalendarView;

/**
 * Created by gopaychan on 2017/3/4.
 */

public interface IMainPresenter {
    void extract7z();

    void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth);
}
