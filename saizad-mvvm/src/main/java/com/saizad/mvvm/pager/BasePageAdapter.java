package com.saizad.mvvm.pager;

import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sa.easyandroidform.ObjectUtils;
import com.saizad.mvvm.utils.Utils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class BasePageAdapter<F extends Fragment & PagerAdapterListener & BasePagerAdapterContract>
        extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private final SparseArray<Class<? extends F>> items;
    private F mCurrentFragment;
    private int position = 0;
    private final ViewPager viewPager;
    private float sumPosition;
    private int scrollState;
    private final SparseArray<F> fragments = new SparseArray<>();
    private int mCurrentPosition = -1;

    private PageListener<F> pageListener = new PageListener<F>() {
        @Override
        public void onPageReady(@NotNull F page) {

        }

        @Override
        public void onPageSelected(@NotNull F page) {

        }

        @Override
        public void pagePosition(int position) {

        }

        @Override
        public void upcomingPage(int currentPosition, int newPosition, int newPositionVisiblePercent) {

        }

        @Override
        public void pageResetting(int position, int tentativePosition, int tentativePositionPercent) {

        }

    };

    public BasePageAdapter(FragmentManager fm, ViewPager viewPager) {
        this(fm, new SparseArray<>(), viewPager);
    }

    @SafeVarargs
    public BasePageAdapter(FragmentManager fm, ViewPager viewPager, Class<? extends F>... items) {
        super(fm);
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        this.items = new SparseArray<>();
        for (Class<? extends F> item : items) {
            fragments.put(this.items.size(), (F) fm.findFragmentByTag(makeFragmentName(viewPager.getId(), this.items.size())));
            this.items.put(this.items.size(), item);
        }
    }

    public BasePageAdapter(FragmentManager fm, SparseArray<Class<? extends F>> items, ViewPager viewPager) {
        super(fm);
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        this.items = new SparseArray<>();
        for (int i = 0; i < items.size(); i++) {
            fragments.put(i, (F) fm.findFragmentByTag(makeFragmentName(viewPager.getId(), i)));
            final Class<? extends F> item = items.valueAt(i);
            this.items.put(this.items.size(), item);
        }
    }

    public void setPageListener(PageListener<F> pageListener) {
        this.pageListener = pageListener;
    }

    public F getCurrentPage() {
        return mCurrentFragment;
    }

    @Override
    public final void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.position = position;
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            BaseViewPager pager = (BaseViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
        if (getCurrentPage() != object) {
            if (ObjectUtils.isNotNull(mCurrentFragment)) {
                mCurrentFragment.onPageUnSelected();
            }
            mCurrentFragment = ((F) object);
            mCurrentFragment.onPageSelected();
            pageListener.onPageSelected(mCurrentFragment);
            pageListener.pagePosition(position);

            mCurrentFragment.pageLoaded()
                    .firstElement()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(__ -> pageReady(getCurrentPage()), throwable -> {
                        Log.d("dasfasf", throwable.getMessage());
                    });
        }
        super.setPrimaryItem(container, position, object);
    }

    public void pageReady(F page) {
        pageListener.onPageReady(page);
    }

    @Override
    public Fragment getItem(int position) {
        final F instance = Utils.createInstance((items.get(position)));
        fragments.put(position, instance);
        return instance;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public SparseArray<Class<? extends F>> getItems() {
        return items;
    }

    public int getPosition() {
        return position;
    }

    public boolean isLastPage() {
        return position + 1 == getCount();
    }

    public boolean isFirstPage() {
        return position == 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int percent = (int) (positionOffset * 100);
        Log.i("StateOnPageScrolled", "Position = " + position);
        Log.i("StateOnPageScrolled", "PositionOffsetPercent = " + percent);
        Log.i("StateOnPageScrolled", "Current Position = " + this.position);
        Log.i("StateOnPageScrolled", "PositionOffset = " + positionOffset);
        Log.i("StateOnPageScrolled", "PositionOffsetPixels = " + positionOffsetPixels);
        Log.i("StateOnPageScrolled", "PositionOffsetVGetPivotX = " + mCurrentFragment.requireView().getPivotX());
        Log.i("StateOnPageScrolled", "PositionOffsetVGetX = " + mCurrentFragment.requireView().getX());
        final int left = mCurrentFragment.requireView().getLeft();
        Log.i("StateOnPageScrolled", "PositionOffsetVGetLeft = " + left);
        final int right = mCurrentFragment.requireView().getRight();
        Log.i("StateOnPageScrolled", "PositionOffsetVGetRight = " + right);
        Log.i("StateOnPageScrolled", "PositionOffsetVGetWidth = " + mCurrentFragment.requireView().getWidth());
        Log.i("StateOnPageScrolled", "PositionOffsetVGetScrollX = " + viewPager.getScrollX());

        final boolean between = isBetween(Math.min(left, right), Math.max(left, right), viewPager.getScrollX());
        final int visiblePercent = 100 - percent;
        if (positionOffsetPixels > 0 && scrollState == ViewPager.SCROLL_STATE_DRAGGING) {
            int nextPosition;
            if (position + positionOffset > sumPosition) {
                nextPosition = this.position + 1;
                if (between) {
                    //right
                    fragments.get(this.position).onPageHiding(visiblePercent);
                    fragments.get(nextPosition).onPageShowing(percent);
                    pageListener.upcomingPage(this.position, nextPosition, percent);
                    Log.i("upcomingPage", "RIGHT CP=" + this.position + " NP=" + nextPosition + " %=" + percent);
                } else {
                    //left resetting
                    fragments.get(this.position).onPageResetting(percent);
                    pageListener.pageResetting(this.position, this.position - 1, visiblePercent);
                }
            } else {
                nextPosition = this.position - 1;
                if (!between) {
                    //left
                    fragments.get(this.position).onPageHiding(percent);
                    fragments.get(nextPosition).onPageShowing(visiblePercent);
                    pageListener.upcomingPage(this.position, nextPosition, visiblePercent);
                    Log.i("upcomingPage", "LEFT  CP=" + this.position + " NP=" + nextPosition + " %=" + visiblePercent);
                } else {
                    //right resetting
                    fragments.get(this.position).onPageResetting(visiblePercent);
                    pageListener.pageResetting(this.position, this.position + 1, percent);
                }
            }
        } else if (scrollState == ViewPager.SCROLL_STATE_SETTLING) {
            if (position + positionOffset > sumPosition) {
                //right
                if (between) {
                    fragments.get(this.position -1).onPageHiding(visiblePercent);
                    fragments.get(this.position).onPageShowing(percent);
                    pageListener.upcomingPage(this.position - 1, this.position, 100);
                } else {
                    //left resetting
                    fragments.get(this.position).onPageResetting(100);
                    pageListener.pageResetting(this.position, this.position - 1, 0);
                }
            } else {
                //left
                if (!between) {
                    fragments.get(this.position + 1).onPageHiding(percent);
                    fragments.get(this.position).onPageShowing(visiblePercent);
                    pageListener.upcomingPage(this.position + 1, this.position, 100);
                } else {
                    //right resetting
                    fragments.get(this.position).onPageResetting(100);
                    pageListener.pageResetting(this.position, this.position + 1, 0);
                }
            }
        }

        sumPosition = position + positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        if (mCurrentFragment != null)
            this.position = position;
        Log.i("StateOnPageSelected", "Position = " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i("StateOnPageScrollStateC", "Position = " + position);
        scrollState = state;
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE: {
                Log.i("StateOnPageScrollStateC", "SCROLL_STATE_IDLE");
                break;
            }
            case ViewPager.SCROLL_STATE_SETTLING: {
                Log.i("StateOnPageScrollStateC", "SCROLL_STATE_SETTLING");
                break;
            }
            case ViewPager.SCROLL_STATE_DRAGGING: {
                Log.i("StateOnPageScrollStateC", "SCROLL_STATE_DRAGGING");
                break;
            }
        }
    }

    public static boolean isBetween(int less, int more, int value) {
        return (value >= less && value <= more);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
