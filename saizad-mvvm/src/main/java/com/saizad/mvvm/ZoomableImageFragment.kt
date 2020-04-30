package com.saizad.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.saizad.mvvm.utils.ImageUtils
import kotlinx.android.synthetic.main.fragment_zoomable_image.*


class ZoomableImageFragment : FullScreenMediaFragment() {
    companion object {
        fun showDialog(fragmentManager: FragmentManager, url: String?, token: String? = null) {
            showDialog(
                ZoomableImageFragment(),
                fragmentManager,
                url,
                token
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zoomable_image, container, false)
    }

    override fun showMedia(url: String, token: String?) {
        ImageUtils.displayImageWithResponse(zoomable_img, url, token) {
            loading_img_progress_bar.isVisible = false
        }
        close.setOnClickListener {
            dismiss()
        }
    }

}
