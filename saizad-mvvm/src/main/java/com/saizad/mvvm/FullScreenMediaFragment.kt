package com.saizad.mvvm

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


abstract class FullScreenMediaFragment : DialogFragment() {

    companion object {
        fun showDialog(fragment: FullScreenMediaFragment, fragmentManager: FragmentManager, url: String?, token: String? = null) {
            val bundle = Bundle()
            bundle.putString(IntentKey.URL_KEY, url)
            bundle.putString("token", token)
            fragment.arguments = bundle
            val ft = fragmentManager.beginTransaction()
            fragment.show(ft, "Zoomable")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(IntentKey.URL_KEY)!!
        val token = arguments?.getString("token")
        showMedia(url, token)
    }

    open fun showMedia(url: String, token: String?){

    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }
}
