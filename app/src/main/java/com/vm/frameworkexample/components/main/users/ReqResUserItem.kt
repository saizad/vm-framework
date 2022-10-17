package com.vm.frameworkexample.components.main.users

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.vm.framework.FullWidthListItem
import com.vm.frameworkexample.R
import com.vm.frameworkexample.models.ReqResUser

class ReqResUserItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FullWidthListItem<ReqResUser>(context, attrs, defStyleAttr) {

    override fun bind(i: ReqResUser) {
        super.bind(i)
        Glide.with(context)
            .load(i.avatar)
            .transform(CircleCrop(), CenterCrop())
            .into(findViewById(R.id.avatar))

        findViewById<TextView>(R.id.fullNameField).text = i.fullName
        findViewById<TextView>(R.id.email).text = i.email
        val job = findViewById<TextView>(R.id.job)
        job.text = i.job
        job.isVisible = i.job != null
    }

    override fun itemGapSize(): Int {
        return resources.getDimensionPixelSize(R.dimen.space_1x)
    }
}