package com.saizad.mvvm.ui.calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sa.easyandroidfrom.ObjectUtils;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

public class TimePickerFragment extends BaseCalendarPickerFragment
                            implements TimePickerDialog.OnTimeSetListener {

    private final @Nullable DateTime dateTime;

    public TimePickerFragment(@Nullable DateTime dateTime, @NonNull DateSelectedListener dateSelectedListener) {
        super(dateSelectedListener);
        this.dateTime = dateTime;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateTime dateTime = ObjectUtils.coalesce(this.dateTime, new DateTime());
        return new TimePickerDialog(getActivity(), this, dateTime.getHourOfDay(), dateTime.minuteOfHour().get(),
                false);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final DateTime dateTime = new DateTime().withTimeAtStartOfDay().plusHours(hourOfDay).plusMinutes(minute);
        dateSelectedListener.selected(dateTime);
    }
}