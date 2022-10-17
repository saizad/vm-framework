package com.vm.frameworkexample.components.main.users.userpage

import android.os.Bundle
import android.view.View
import com.vm.framework.pager.PageListenerImp
import com.vm.framework.utils.*
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.main.MainFragment
import com.vm.frameworkexample.databinding.FragmentUserPageHostBinding
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.take

@AndroidEntryPoint
class UserPageHostFragment : MainFragment<UserPageHostViewModel>() {

    private var userPageAdapter: UserPageAdapter? = null
    private lateinit var binding: FragmentUserPageHostBinding

    override val viewModelClassType: Class<UserPageHostViewModel>
        get() = UserPageHostViewModel::class.java

    override fun layoutRes(): Int {
        return R.layout.fragment_user_page_host
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        super.onViewCreated(view, savedInstanceState, recycled)
        binding = FragmentUserPageHostBinding.bind(view)
        
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())

        lifecycleScopeOnMain {
            viewModel().users
                .combinePair(viewModel().currentSelected.take(1))
                .collect {
                    val usersList = it.first
                    val selectUser = it.second
                    initPage(usersList, selectUser)
                }
        }

        lifecycleScopeOnMain {
            binding.refreshAll.flowThrottleClick()
                .flatMapLatest {
                    viewModel().users.combinePair(viewModel().currentSelected).take(1)
                }
                .collect {
                    val usersList = it.first
                    val selectUser = it.second
                    initPage(usersList, selectUser)
                }
        }

        binding.prevButton.throttleClick {
            binding.viewPager.prev(false)
        }

        binding.nextButton.throttleClick {
            binding.viewPager.next(false)
        }
    }

    override fun persistView(): Boolean {
        return false
    }

    private fun initPage(users: List<ReqResUser>, user: ReqResUser?) {

        userPageAdapter?.setPageListener(null)

        userPageAdapter =
            UserPageAdapter(this, users.map {
                UserPageFragment::class.java
            }, binding.viewPager)
        binding.viewPager.adapter = userPageAdapter
        user?.let {
            binding.viewPager.setCurrentItem(users.indexOfFirst { it.id == user.id }, false)
        }

        userPageAdapter!!.setPageListener(object : PageListenerImp<UserPageFragment>() {
            override fun onPageLoaded(page: UserPageFragment, position: Int) {
                page.viewModel().setUser(users[position])
            }

            override fun onPageReady(page: UserPageFragment) {
                page.pageOnScreen()
                viewModel().setCurrentUser(users[binding.viewPager.currentItem])
            }
        })
    }

    override fun onBackPressed(): Boolean {
        if (binding.viewPager.isFirstPage) {
            return super.onBackPressed()
        }
        binding.viewPager.prev()
        return true
    }
}
