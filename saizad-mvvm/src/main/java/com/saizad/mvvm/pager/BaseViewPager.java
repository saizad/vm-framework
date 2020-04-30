package com.saizad.mvvm.pager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


public class BaseViewPager extends ViewPager {

  public BaseViewPager(@NonNull Context context) {
    super(context);
  }

  public BaseViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public void next(){
    setCurrentItem(getCurrentItem()+1, true);
  }

  public void previous(){
    setCurrentItem(getCurrentItem()-1, true);
  }
}
