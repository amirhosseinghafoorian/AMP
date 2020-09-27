package com.a.amp.user.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    val listNames = mutableListOf<String>()
    val listFragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int {
        return listFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }

    fun addFragment(fragment: Fragment, name: String) {
        listFragments.add(fragment)
        listNames.add(name)
    }

    fun getName(position: Int):String = listNames[position]

}