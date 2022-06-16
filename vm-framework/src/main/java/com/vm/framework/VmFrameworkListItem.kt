package com.vm.framework

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.CallSuper
import com.vm.framework.utils.dpToPxInt
import com.vm.framework.utils.setMarginBottom
import sa.zad.pagedrecyclerlist.ConstraintLayoutItem

abstract class VmFrameworkListItem<M> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayoutItem<M>(context, attrs, defStyleAttr) {

    var clipVerticalLastItemSpacing = true


    @CallSuper
    override fun lastItem(last: Boolean) {
        if (!last || !clipVerticalLastItemSpacing) {
            margins(itemGapSize())
        } else {
            margins(0)
        }
    }

    private fun margins(itemGapSize : Int){
        layoutParams?.let {
            with(it as MarginLayoutParams) {
                bottomMargin = itemGapSize
                layoutParams = this
            }
        }
    }

    protected open fun itemGapSize(): Int = context.dpToPxInt(4f)
}
