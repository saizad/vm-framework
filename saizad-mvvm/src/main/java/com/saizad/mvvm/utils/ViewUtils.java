package com.saizad.mvvm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


public class ViewUtils {


    public static void hideBottomBar(@NonNull HideBottomViewOnScrollBehavior<View> hideBottomViewOnScrollBehavior, boolean show, View view) {
        if (show) {
            ViewUtils.switchVisibility(view, true);
            hideBottomViewOnScrollBehavior.slideUp(view);
        } else {
            hideBottomViewOnScrollBehavior.slideDown(view);
            Handler handler = new Handler();
            handler.postDelayed(() -> ViewUtils.switchVisibility(view, false), 200);
        }
    }


    public static void setDisable(View view) {
        setDisable(view, 0.5f);
    }

    public static void setDisable(View view, float alpha) {
        view.setEnabled(false);
        view.setAlpha(alpha);
    }

    public static void setEnable(View view) {
        view.setEnabled(true);
        view.setAlpha(1f);
    }

    public static void enable(boolean enable, View view) {
        enable(enable, view, 0.5f);
    }

    public static void enable(boolean enable, View view, float alpha) {
        if (enable) {
            setEnable(view);
        } else {
            setDisable(view, alpha);
        }
    }

    public static View inflate(Context context, @LayoutRes int layoutRes) {
        return inflate(context, layoutRes, null);
    }

    public static View inflate(Context context, @LayoutRes int layoutRes,
                               @Nullable ViewGroup viewGroup) {
        return inflate(context, layoutRes, viewGroup, false);
    }

    public static View inflate(Context context, @LayoutRes int layoutRes,
                               @Nullable ViewGroup viewGroup, boolean attachToRoot) {
        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return mInflater.inflate(layoutRes, viewGroup, attachToRoot);
    }

    public static void switchVisibility(View view) {
        switchVisibility(view, view.getVisibility() != View.VISIBLE);
    }

    public static void switchVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static void visibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void expand(final View v) {
        if (v.getVisibility() == View.VISIBLE) {
            return;
        }

        v.measure(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int interpolateHeight = (int) (targetHeight * interpolatedTime);
                if (interpolatedTime == 1) {
                    v.getLayoutParams().height = AppBarLayout.LayoutParams.WRAP_CONTENT;
                } else {
                    v.getLayoutParams().height = interpolateHeight;
                }
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration(500);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        if (v.getVisibility() == View.GONE) {
            return;
        }

        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                    v.getLayoutParams().height = AppBarLayout.LayoutParams.WRAP_CONTENT;
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                }
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration(500);
        v.startAnimation(a);
    }

    public static void switchFabButtonVisibility(@NonNull FloatingActionButton floatingActionButton) {
        switchFabButtonVisibility(floatingActionButton, !floatingActionButton.isShown());
    }

    public static void switchFabButtonVisibility(@NonNull FloatingActionButton floatingActionButton,
                                                 boolean show) {
        if (show) {
            floatingActionButton.show();
        } else {
            floatingActionButton.hide();
        }
    }

    public static void switchFabButtonVisibility(@NonNull ExtendedFloatingActionButton extendedFloatingActionButton,
                                                 boolean show) {
        if (show) {
            extendedFloatingActionButton.show();
        } else {
            extendedFloatingActionButton.hide();
        }
    }

    public static void bindClick(View view, Consumer<Object> onNext) {
        bindClick(view, onNext, throwable -> {
        });
    }

    public static void bindClick(View view, Consumer<Object> onNext, Consumer<Throwable> throwable) {
        bindClick(view, onNext, throwable, () -> {
        });
    }

    public static void bindClick(View view, Consumer<Object> onNext, Consumer<Throwable> throwable,
                                 Action onComplete) {
        bindClick(view)
                .subscribe(onNext, throwable, onComplete);
    }

    public static Observable<Object> bindClick(View view) {
        return RxView.clicks(view)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Boolean> showDialog(Activity activity, String message) {
        PublishSubject<Boolean> callbackSubject = PublishSubject.create();
        new AlertDialog.Builder(activity).setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> callbackSubject.onNext(true))
                .setNegativeButton("Cancel", (dialog, which) -> callbackSubject.onNext(false))
                .setOnCancelListener(dialog -> callbackSubject.onNext(false))
                .create()
                .show();
        return callbackSubject.take(1);
    }

    public static AlertDialog.Builder dialogList(@NonNull Context context, String title, DialogInterface.OnClickListener listener, CharSequence[] items) {
        return new AlertDialog.Builder(context).setTitle(title).setItems(items, listener);
    }
}
