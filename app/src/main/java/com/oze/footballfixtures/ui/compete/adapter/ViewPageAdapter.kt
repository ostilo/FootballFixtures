package com.oze.footballfixtures.ui.compete.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager): FragmentStatePagerAdapter(supportFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<Fragment>()
    private val fragmentListTitles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentListTitles[position]
    }

    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        fragmentListTitles.add(title)
    }
}
