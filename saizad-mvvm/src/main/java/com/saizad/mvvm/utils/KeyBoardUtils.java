package com.saizad.mvvm.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyBoardUtils {

  public final static int KEYBOARD_DISPLAY_DURATION = 200;

  public static void hide(Context context, View view) {
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      view.clearFocus();
    }
  }

  public static void hide(Activity activity) {
    hide(activity, activity.getCurrentFocus());
  }

  public static void initHidden(Activity activity) {
    activity.getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }

  public static void show(Context context, EditText editText) {
    show(context, editText, 0);
  }

  public static void show(final Context context, final EditText editText, int delayTime) {
    Runnable showKeyboardDelay = new Runnable() {
      @Override
      public void run() {
        if (context != null) {
          InputMethodManager imm =
              (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
      }
    };
    new Handler().postDelayed(showKeyboardDelay, delayTime);
  }
}