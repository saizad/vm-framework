package com.saizad.mvvm.components.form.ui;

import android.content.Context;
import android.util.AttributeSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringInputFieldView extends InputFieldView<String> {

    public StringInputFieldView(@NotNull Context context) {
        super(context);
    }

    public StringInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StringInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String resolveFrom(@NotNull CharSequence charSequence) {
        if(charSequence.toString().isEmpty()){
            return null;
        }
        return charSequence.toString();
    }

    @Override
    public CharSequence resolve(@Nullable String value) {
        return value;
    }

    @Override
    public boolean isSame(String value, String prevValue) {
        return value.equals(prevValue);
    }
}
