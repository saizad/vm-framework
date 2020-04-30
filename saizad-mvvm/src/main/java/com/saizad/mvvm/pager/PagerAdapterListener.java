package com.saizad.mvvm.pager;

public interface PagerAdapterListener {
    void onPageSelected();

    void onPageUnSelected();

    //  void onPageSettling();
    //  void onPageIdle();
    void onPageShowing(int visiblePercent);

    void onPageHiding(int visiblePercent);

    void onPageResetting(int visiblePercent);
}