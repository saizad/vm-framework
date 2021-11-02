package com.vm.frameworkexample.components.main.email

import android.content.Context
import android.util.AttributeSet
import android.view.View


import com.vm.frameworkexample.R
import sa.zad.pagedrecyclerlist.IntegerPageKeyList
import com.vm.frameworkexample.models.ReqResUser


class EmailContentList @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : IntegerPageKeyList<String, EmailContentItem>(context, attrs, defStyle) {

    override fun getSelectorItem(context: Context, viewType: Int): EmailContentItem {
        return View.inflate(context, R.layout.item_email_content, null) as EmailContentItem
    }
}