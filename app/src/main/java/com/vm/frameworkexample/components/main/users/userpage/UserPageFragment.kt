package com.vm.frameworkexample.components.main.users.userpage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.vm.framework.utils.throttleClick
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.main.MainPageFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_page.*

@AndroidEntryPoint
class UserPageFragment : MainPageFragment<UserPageViewModel>() {


    override val viewModelClassType: Class<UserPageViewModel>
        get() = UserPageViewModel::class.java

    override fun layoutRes(): Int {
        return R.layout.fragment_user_page
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel().liveData.observe(viewLifecycleOwner) {
            fullName.text = it.fullName
            email.text = it.email
            Glide.with(requireContext())
                .load(it.avatar)
                .transform(CenterCrop())
                .into(bigAvatar)

            bigAvatar.throttleClick {
                openFragment(R.id.updateUserFragment, {
                    putParcelable("user", it)
                })
            }
        }
    }

    fun pageOnScreen(){
        log("pageOnScreen ${pageIndex} ${lifecycle.currentState}")
    }

}
