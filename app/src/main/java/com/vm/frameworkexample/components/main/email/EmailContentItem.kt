package com.vm.frameworkexample.components.main.email

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.vm.framework.FullWidthListItem
import com.vm.framework.VmFrameworkListItem
import kotlinx.android.synthetic.main.item_req_res_user.view.*
import com.vm.frameworkexample.R
import com.vm.frameworkexample.models.ReqResUser;
import kotlinx.android.synthetic.main.item_email_content.view.*

class EmailContentItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : VmFrameworkListItem<String>(context, attrs, defStyleAttr) {

    override fun bind(i: String) {
        super.bind(i)
        content.text = i

    }
    override fun itemGapSize(): Int {
        return resources.getDimensionPixelSize(R.dimen.space_1x)
    }
}