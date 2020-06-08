package com.saizad.mvvmexample.components.main.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.saizad.mvvmexample.R;
import com.saizad.mvvmexample.models.User;

import sa.zad.pagedrecyclerlist.IntegerPageKeyList;


public class UserList extends IntegerPageKeyList<User, UserListItem> {
    public UserList(Context context) {
        this(context, null);
    }

    public UserList(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserList(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public UserListItem getSelectorItem(Context context, int viewType) {
        return (UserListItem) View.inflate(context, R.layout.item_user_list, null);
    }
}
