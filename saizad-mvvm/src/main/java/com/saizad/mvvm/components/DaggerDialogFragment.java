

package com.saizad.mvvm.components;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;


public abstract class DaggerDialogFragment extends DialogFragment implements HasAndroidInjector {

  @Inject DispatchingAndroidInjector<Object> androidInjector;

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public AndroidInjector<Object> androidInjector() {
    return androidInjector;
  }
}
