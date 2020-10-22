package com.saizad.mvvm.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


public class BaseViewPager extends ViewPager {

    private View mCurrentView;

    public BaseViewPager(@NonNull Context context) {
        super(context);
    }

    public BaseViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public final void next() {
        next(true);
    }

    public final void next(boolean smoothScroll) {
        setCurrentItem(getCurrentItem() + 1, smoothScroll);
    }

    public final void previous() {
        previous(true);
    }

    public final void previous(boolean smoothScroll) {
        setCurrentItem(getCurrentItem() - 1, smoothScroll);
    }

    public final boolean isLastPage() {
        return getCurrentItem() == getAdapter().getCount() - 1;
    }

    public final boolean isFirstPage() {
        return getCurrentItem() == 0;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mCurrentView == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int height = 0;
        mCurrentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int h = mCurrentView.getMeasuredHeight();
        if (h > height) height = h;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void measureCurrentView(View currentView) {
        mCurrentView = currentView;
        requestLayout();
    }

    public int measureFragment(View view) {
        if (view == null)
            return 0;

        view.measure(0, 0);
        return view.getMeasuredHeight();
    }
}
