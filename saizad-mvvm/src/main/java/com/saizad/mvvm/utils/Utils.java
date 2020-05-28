package com.saizad.mvvm.utils;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.annotations.SerializedName;
import com.sa.easyandroidform.Func1;
import com.sa.easyandroidform.ObjectUtils;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Utils {

    public static final String DOB_DATE_FORMATTER = "yyyy-MMM-dd";
    public static final String APP_DATE_FORMATTER = "yyyy-MM-dd";
    public static final String APP_TIME_FORMATTER = "h:mm:ss";
    public static final String APP_TIME_FORMATTER_24_HOURS = "H:mm:ss";

    public static String ordinal(int i) {
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];

        }
    }

    @Nullable
    public static <T extends Fragment> T createInstance(Class<T> tClass) {
        try {
            final T t = tClass.newInstance();
            Bundle args = new Bundle();
            t.setArguments(args);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static <I> I compareFindItem(List<I> list, Func1<I, Boolean> callback) {
        for (int i = 0; i < list.size(); i++) {
            final I t = list.get(i);
            if (callback.call(t)) {
                return t;
            }
        }
        return null;
    }

    public static <I> int compareFindIndex(List<I> list, Func1<I, Boolean> callback) {
        for (int i = 0; i < list.size(); i++) {
            if (callback.call(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static <E, V> List<V> extractValue(@NonNull final List<E> list,
                                              @NonNull final Func1<E, V> callback) {
        List<V> returnList = new ArrayList<>();
        for (E e : list) {
            final V r = callback.call(e);
            if (ObjectUtils.isNotNull(r)) {
                returnList.add(r);
            }
        }
        return returnList;
    }


    public static Float decimalN(float value, int n) {
        return decimalN(value, n, RoundingMode.CEILING);
    }

    public static Float decimalN(float value, int n, RoundingMode roundingMode) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append("#");
        }
        final DecimalFormat decimalFormat = new DecimalFormat("#." + stringBuilder.toString());
        decimalFormat.setRoundingMode(roundingMode);
        return Float.valueOf(decimalFormat.format(value));
    }

    public static String removeTrailingZeros(float value) {
        return decimalN(value, 2).toString().replaceAll("\\.?0*$", "");
    }

    public static String printDataToForm(Class<?> data) {
        StringBuilder sb = new StringBuilder();
        for (Field field : data.getDeclaredFields()) {
            if (field.isAnnotationPresent(SerializedName.class)) {
                final String simpleName = field.getType().getSimpleName();
                final SerializedName annotation = field.getAnnotation(SerializedName.class);
                if (annotation != null) {
                    sb.append("public final Field<").append(simpleName).append("> ").append(field.getName()).append("Field;\n");
                }
            }
        }
        sb.append("public Form(){\nthis(new ").append(data.getSimpleName()).append("());\n}\n\npublic Form(").append(data.getSimpleName()).append(" data) {\nsuper(asList(");
        for (Field field : data.getDeclaredFields()) {
            if (field.isAnnotationPresent(SerializedName.class)) {
                final String simpleName = field.getType().getSimpleName();
                final SerializedName annotation = field.getAnnotation(SerializedName.class);
                if (annotation != null) {
                    final String value = annotation.value();
                    sb.append("new Field<>(\"").append(value).append("\"").append(", data.").append(field.getName()).append(", true),\n");
                }
            }
        }
        sb.deleteCharAt(sb.length() - 2).append("));\n\n");
        for (Field field : data.getDeclaredFields()) {
            if (field.isAnnotationPresent(SerializedName.class)) {
                final SerializedName annotation = field.getAnnotation(SerializedName.class);
                if (annotation != null) {
                    final String value = annotation.value();
                    final String s = "\"" + value + "\"";
                    sb.append(field.getName()).append("Field = getField(").append(s).append(");\n");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Nullable
    public static Uri parseUrl(String url) {
        if (url != null) {
            return Uri.parse(url);
        }
        return null;
    }
}
