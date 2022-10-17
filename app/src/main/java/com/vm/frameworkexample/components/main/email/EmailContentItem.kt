package com.vm.frameworkexample.components.main.email

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.vm.framework.VmFrameworkListItem
import com.vm.frameworkexample.R

class EmailContentItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : VmFrameworkListItem<String>(context, attrs, defStyleAttr) {

    override fun bind(i: String) {
        super.bind(i)
        findViewById<TextView>(R.id.content).text = i

    }
    override fun itemGapSize(): Int {
        return resources.getDimensionPixelSize(R.dimen.space_1x)
    }
}