package com.saizad.mvvm.components.form.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.saizad.mvvm.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

public class DOBStringInputFieldView extends InputFieldView<String> {

    public DOBStringInputFieldView(@NotNull Context context) {
        super(context);
    }

    public DOBStringInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DOBStringInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String resolveFrom(@NotNull CharSequence charSequence) {
        if(charSequence.toString().isEmpty()){
            return null;
        }
        return DateTime.parse(charSequence.toString()).toString(Utils.APP_DATE_FORMATTER);
    }

    @Override
    public CharSequence resolve(@Nullable String value) {
        if(value == null){
            return  null;
        }
        return DateTime.parse(value).toString(Utils.APP_DATE_FORMATTER);
    }

    @Override
    public boolean isSame(String value, String prevValue) {
        return value.equals(prevValue);
    }
}
