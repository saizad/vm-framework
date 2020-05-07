package com.saizad.mvvm.components.form.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.sa.easyandroidfrom.ObjectUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloatInputFieldView extends InputFieldView<Float> {

    public FloatInputFieldView(@NotNull Context context) {
        super(context);
    }

    public FloatInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatInputFieldView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Float resolveTo(@NotNull CharSequence charSequence) {
        try {
            return Float.valueOf(charSequence.toString());
        }catch (NumberFormatException e){
            return null;
        }
    }

    @Override
    public CharSequence setText(@Nullable Float value) {
        if(ObjectUtils.isNull(value)){
            return null;
        }
        return value.toString();
    }

    @Override
    public boolean compare(Float value, Float prevValue) {
        return value.equals(prevValue);
    }
}
