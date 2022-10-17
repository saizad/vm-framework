package com.vm.framework.components.form.ui

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.View
import com.vm.framework.R
import com.vm.framework.databinding.LibImageFieldViewBinding
import com.vm.framework.utils.ImageUtils


open class ImageFieldView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FilesFieldView(context, attrs, defStyleAttr) {

    lateinit var libImageFieldViewBinding: LibImageFieldViewBinding

    override fun showValue(field: List<String>?) {
        super.showValue(field)
        ImageUtils.setAvatarImage(libImageFieldViewBinding.imageView, field?.first())
    }

    override fun initView(): View {
        return libImageFieldViewBinding.editAvatar
    }

    override fun undoView(): View? {
        return libImageFieldViewBinding.undoAvatar
    }

    override fun clearView(): View? {
        return libImageFieldViewBinding.deleteAvatar
    }
    
    init {
        val inflate = View.inflate(getContext(), R.layout.lib_image_field_view, this)
        LibImageFieldViewBinding.bind(inflate)
    }

    override fun intent(): Intent {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        intent.type = "image/*"
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("outputX", 256)
        intent.putExtra("outputY", 256)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("return-customers", true)
        return intent
    }
}
