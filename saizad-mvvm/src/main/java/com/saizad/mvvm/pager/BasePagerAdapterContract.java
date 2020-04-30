package com.saizad.mvvm.pager;

import io.reactivex.Observable;

public interface BasePagerAdapterContract {
  Observable<Boolean> pageLoaded();
}