package com.vm.frameworkexample.components.main.users

import android.content.Context
import android.util.AttributeSet
import android.view.View


import com.vm.frameworkexample.R
import sa.zad.pagedrecyclerlist.IntegerPageKeyList
import com.vm.frameworkexample.models.ReqResUser


class ReqResUserList @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : IntegerPageKeyList<ReqResUser, ReqResUserItem>(context, attrs, defStyle) {

    override fun getSelectorItem(context: Context, viewType: Int): ReqResUserItem {
        return View.inflate(context, R.layout.item_req_res_user, null) as ReqResUserItem
    }
}