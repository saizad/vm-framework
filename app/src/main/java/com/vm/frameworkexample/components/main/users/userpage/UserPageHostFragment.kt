package com.vm.frameworkexample.components.main.users.userpage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.vm.framework.pager.PageListenerImp
import com.vm.framework.utils.isFirstPage
import com.vm.framework.utils.next
import com.vm.framework.utils.prev
import com.vm.framework.utils.throttleClick
import com.vm.frameworkexample.R
import com.vm.frameworkexample.components.main.MainFragment
import com.vm.frameworkexample.models.ReqResUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_page_host.*

@AndroidEntryPoint
class UserPageHostFragment : MainFragment<UserPageHostViewModel>() {
    
    override val viewModelClassType: Class<UserPageHostViewModel>
        get() = UserPageHostViewModel::class.java

    override fun layoutRes(): Int {
        return R.layout.fragment_user_page_host
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, recycled: Boolean) {
        super.onViewCreated(view, savedInstanceState, recycled)
        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        viewModel().initLiveData.observe(viewLifecycleOwner, Observer {
            val usersList = it.first
            val selectUser = it.second
            initPage(usersList, selectUser)
        })

        nextButton.throttleClick {
            viewPager.next(false)
        }
    }

    override fun persistView(): Boolean {
        return false
    }
    
    private fun initPage(users: List<ReqResUser>, user: ReqResUser?){
        val userPageAdapter =
            UserPageAdapter(this, users.map {
                UserPageFragment::class.java
            }, viewPager)
        viewPager.adapter = userPageAdapter
        user?.let {
            viewPager.setCurrentItem(users.indexOfFirst { it.id == user.id }, false)
        }

        userPageAdapter.setPageListener(object : PageListenerImp<UserPageFragment>() {
            override fun onPageLoaded(page: UserPageFragment, position: Int) {
                page.viewModel().setUser(users[position])
            }

            override fun onPageReady(page: UserPageFragment) {
                page.pageOnScreen()
                viewModel().setCurrentUser(users[viewPager.currentItem])
            }
        })
    }

    override fun onBackPressed(): Boolean {
        if(viewPager.isFirstPage) {
            return super.onBackPressed()
        }
        viewPager.prev()
        return true
    }
}
