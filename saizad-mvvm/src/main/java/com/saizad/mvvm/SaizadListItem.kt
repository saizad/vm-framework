package com.saizad.mvvm

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sa.zad.pagedrecyclerlist.ConstraintLayoutItem

abstract class SaizadListItem<M> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayoutItem<M>(context, attrs, defStyleAttr){

    init {
        setFullWidth()
    }

    protected fun setFullWidth(){
        layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun lastItem(hide: Boolean) {
        if (!hide) {
            if (layoutParams is MarginLayoutParams) {
                val layoutParams = layoutParams as MarginLayoutParams
                layoutParams.setMargins(0, 0, 0, itemGapSize())
                requestLayout()
            }
        }
    }

    open protected fun itemGapSize(): Int = 10
}
