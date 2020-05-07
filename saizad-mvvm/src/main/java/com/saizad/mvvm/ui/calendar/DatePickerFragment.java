package com.saizad.mvvm.ui.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

public class DatePickerFragment extends BaseCalendarPickerFragment {

    private final DateTime dateTime;
    private final DateTime minDateTime;

    public DatePickerFragment(@NonNull DateSelectedListener dateSetListener) {
        this(new DateTime(), new DateTime(0), dateSetListener);
    }

    public DatePickerFragment(@NonNull DateTime dateTime, @NonNull DateSelectedListener dateSetListener) {
        this(dateTime, new DateTime(0), dateSetListener);
    }

    public DatePickerFragment(@NonNull DateTime dateTime, @NonNull DateTime minDateTime, @NonNull DateSelectedListener dateSelectedListener) {
        super(dateSelectedListener);
        this.dateTime = dateTime;
        this.minDateTime = minDateTime;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year1, month1, dayOfMonth) -> {
            dateSelectedListener.selected(DateTime.parse(year1 + "-" + (month1 + 1) + "-" + dayOfMonth));
        }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());
        datePickerDialog.getDatePicker().setMinDate(minDateTime.getMillis());
        return datePickerDialog;
    }
}