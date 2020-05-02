package com.saizad.mvvm.ui.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

public class DatePickerFragment extends BaseCalendarPickerFragment {

    private final DateTime dateTime;

    public DatePickerFragment(@NonNull DateSelectedListener dateSetListener) {
        this(new DateTime(), dateSetListener);
    }

    public DatePickerFragment(@NonNull DateTime dateTime, @NonNull DateSelectedListener dateSelectedListener) {
        super(dateSelectedListener);
        this.dateTime = dateTime;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), (view, year1, month1, dayOfMonth) -> {
            dateSelectedListener.selected(DateTime.parse(year1 + "-" + (month1 + 1) + "-" + dayOfMonth));
        }, dateTime.getYear(), dateTime.getMonthOfYear()-1, dateTime.getDayOfMonth());
    }
}