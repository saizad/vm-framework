package com.vm.frameworkexample.components.main.users.userpage

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.vm.framework.utils.throttleClick
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.main.MainPageFragment
import com.vm.frameworkexample.databinding.FragmentUserPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserPageFragment : MainPageFragment<UserPageViewModel>() {

    private lateinit var binding: FragmentUserPageBinding

    override val viewModelClassType: Class<UserPageViewModel>
        get() = UserPageViewModel::class.java

    override fun layoutRes(): Int {
        return R.layout.fragment_user_page
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserPageBinding.bind(view)
        viewModel().liveData.observe(viewLifecycleOwner) {
            binding.fullName.text = it.fullName
            binding.email.text = it.email
            Glide.with(requireContext())
                .load(it.avatar)
                .transform(CenterCrop())
                .into(binding.bigAvatar)

            binding.bigAvatar.throttleClick {
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
