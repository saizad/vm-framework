package com.saizad.mvvmexample.components.main.home

import android.content.Context
import android.util.AttributeSet
import android.view.View


import androidx.annotation.Nullable
import com.saizad.mvvmexample.R
import sa.zad.pagedrecyclerlist.IntegerPageKeyList
import com.saizad.mvvmexample.models.User


class UserList @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : IntegerPageKeyList<User, UserItem>(context, attrs, defStyle) {

    override fun getSelectorItem(context: Context, viewType: Int): UserItem {
        return View.inflate(context, R.layout.item_user, null) as UserItem
    }
}