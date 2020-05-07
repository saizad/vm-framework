package com.saizad.mvvm.components.form.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.sa.easyandroidfrom.ObjectUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerInputFieldView extends InputFieldView<Integer> {

    public IntegerInputFieldView(@NotNull Context context) {
        super(context);
    }

    public IntegerInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IntegerInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public Integer resolveTo(@NotNull CharSequence charSequence) {
        try {
            return Integer.valueOf(charSequence.toString());
        }catch (NumberFormatException e){
            return null;
        }
    }

    @Override
    public CharSequence setText(@Nullable Integer value) {
        if(ObjectUtils.isNull(value)){
            return null;
        }
        return value.toString();
    }

    @Override
    public boolean compare(Integer value, Integer prevValue) {
        return value.equals(prevValue);
    }
}
