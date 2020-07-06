package com.saizad.mvvm.pager;

public interface PagerAdapterListener {
    void onPageSelected();

    void onPageUnSelected();

    void onPageShowing(int visiblePercent);

    void onPageHiding(int visiblePercent);

    void onPageResetting(int visiblePercent);
}