package com.saizad.mvvm.utils;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sa.easyandroidfrom.Func1;
import com.sa.easyandroidfrom.ObjectUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Utils {

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
}
