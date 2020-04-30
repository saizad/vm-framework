package com.saizad.mvvm;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.loading_dialog);
        setCancelable(false);
    }

    public void show(boolean show){
        if(show){
            show();
            return;
        }
        dismiss();
    }
}
