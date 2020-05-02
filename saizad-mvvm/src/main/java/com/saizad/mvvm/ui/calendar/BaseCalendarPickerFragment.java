package com.saizad.mvvm.ui.calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class BaseCalendarPickerFragment extends DialogFragment {
    protected final DateSelectedListener dateSelectedListener;

    public BaseCalendarPickerFragment(@NonNull DateSelectedListener dateSelectedListener) {
        this.dateSelectedListener = dateSelectedListener;
    }
}
