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
            margins(horizontalGap(), itemGapSize())
        }else{
            margins(horizontalGap(), 0)
        }
    }

    private fun margins(horizontal: Int, itemGapSize : Int){
        if (layoutParams is MarginLayoutParams) {
            val layoutParams = layoutParams as MarginLayoutParams
            layoutParams.setMargins(horizontal, 0, horizontal, itemGapSize)
            requestLayout()
        }
    }
    open protected fun horizontalGap(): Int = 10

    open protected fun itemGapSize(): Int = 10
}
