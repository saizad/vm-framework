package com.saizad.mvvm.ui.calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

public class TimePickerFragment extends BaseCalendarPickerFragment
                            implements TimePickerDialog.OnTimeSetListener {

    @NotNull
    private final DateTime dateTime;

    public TimePickerFragment(@NonNull DateTime dateTime, @NonNull DateSelectedListener dateSelectedListener) {
        super(dateSelectedListener);
        this.dateTime = dateTime;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, dateTime.getHourOfDay(), dateTime.minuteOfHour().get(),
                false);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final DateTime dateTime = this.dateTime.withTimeAtStartOfDay().plusHours(hourOfDay).plusMinutes(minute);
        dateSelectedListener.selected(dateTime);
    }
}