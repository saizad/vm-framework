package com.vm.frameworkexample.components.main.users.userpage

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.vm.framework.pager.BaseFragmentStateAdapter

class UserPageAdapter(
    fragment: Fragment,
    pages: List<Class<UserPageFragment>>,
    viewPager2: ViewPager2
) : BaseFragmentStateAdapter<UserPageFragment>(
        fragment,
        pages, viewPager2
    )