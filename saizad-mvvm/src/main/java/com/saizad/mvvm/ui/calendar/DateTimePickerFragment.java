package com.saizad.mvvm.ui.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

public class DateTimePickerFragment extends BaseCalendarPickerFragment {

    private final DateTime dateTime;

    public DateTimePickerFragment(@NonNull DateSelectedListener dateSetListener) {
        this(new DateTime(), dateSetListener);
    }

    public DateTimePickerFragment(@NonNull DateTime dateTime, @NonNull DateSelectedListener dateSelectedListener) {
        super(dateSelectedListener);
        this.dateTime = dateTime;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), (view, year1, month1, dayOfMonth) -> {
            new TimePickerDialog(getActivity(), (view1, hourOfDay, minute) -> {
                final DateTime dateTime = DateTime.parse(year1 + "-" + (month1 + 1) + "-" + dayOfMonth);
                dateSelectedListener.selected(dateTime.withTimeAtStartOfDay().plusHours(hourOfDay).plusMinutes(minute));
            }, dateTime.getHourOfDay(), dateTime.minuteOfHour().get(), false).show();
        }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());
    }
}