package com.saizad.mvvm.components.form.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.sa.easyandroidfrom.ObjectUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LongInputFieldView extends InputFieldView<Long> {

    public LongInputFieldView(@NotNull Context context) {
        super(context);
    }

    public LongInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LongInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Long resolveTo(@NotNull CharSequence charSequence) {
        try {
            return Long.valueOf(charSequence.toString());
        }catch (NumberFormatException e){
            return null;
        }
    }

    @Override
    public CharSequence setText(@Nullable Long value) {
        if(ObjectUtils.isNull(value)){
            return null;
        }
        return value.toString();
    }

    @Override
    public boolean compare(Long value, Long prevValue) {
        return value.equals(prevValue);
    }
}
