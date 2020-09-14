package com.saizad.mvvm

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saizad.mvvm.utils.dpToPxInt
import sa.zad.pagedrecyclerlist.ConstraintLayoutItem

abstract class SaizadListItem<M> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayoutItem<M>(context, attrs, defStyleAttr){

    var clipVerticalLastItemSpacing = true

    override fun lastItem(last: Boolean) {
        if (!last || !clipVerticalLastItemSpacing) {
            margins(itemGapSize())
        }else {
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
