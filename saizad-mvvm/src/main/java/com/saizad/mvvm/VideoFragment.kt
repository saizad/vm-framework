package com.saizad.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import cn.jzvd.Jzvd
import kotlinx.android.synthetic.main.fragment_video.*


class VideoFragment : FullScreenMediaFragment() {

    companion object {
        fun showDialog(fragmentManager: FragmentManager, url: String?, token: String? = null) {
            showDialog(
                VideoFragment(),
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
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun showMedia(url: String, token: String?) {
        video.setUp(url, "")
//        video.thumbImageView.setImageBitmap(Utils.retriveVideoFrameFromVideo(url))
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}
