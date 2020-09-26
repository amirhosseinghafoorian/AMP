package com.a.amp.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.a.amp.R
import com.a.amp.storage.setting
import com.a.amp.user.ui.ProfileTabFragment
import com.a.amp.user.ui.ProfileTabFragment2
import com.a.amp.user.ui.ProfileViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.viewPager
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var setting: setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.decorView?.systemUiVisibility = 0
        setting = setting()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val loginViewModel = ViewModelProvider(this).get(HomeListViewModel::class.java)

        val myAdapter2 = loginViewModel.summaryList.value?.let { HomeSummaryCvAdapter(it) }
        val myAdapter = loginViewModel.relatedList.value?.let { HomeRelatedCvAdapter(it) }

        homeViewPagerInit()
        home_page_recycle_2.apply {
            adapter = myAdapter2
            setHasFixedSize(true)
        }

        home_page_recycle_1.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        loginViewModel.summaryList.observe(viewLifecycleOwner, { list ->
            myAdapter2?.notifyDataSetChanged()
        })

        loginViewModel.relatedList.observe(viewLifecycleOwner, { list ->
            myAdapter?.notifyDataSetChanged()
        })

        CoroutineScope(Dispatchers.IO).launch {
            loginViewModel.fillSummary()
            loginViewModel.fillRelated()
        }

        home_appbar_end_icon.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                    setting.getString("username"),
                    setting.getString("id")
                )
            )
        }
        home_appbar_start_icon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteFragment(""))
        }
        home_appbar_end_icon_power.setOnClickListener {
            setting.remove("username")
            setting.remove("id")
            setting.remove("token")
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthenticate())
        }
    }

    fun homeViewPagerInit() {
        val viewPagerAdapter = HomeViewPagerAdapter(childFragmentManager, lifecycle)
        viewPagerAdapter.addFragment(ProfileTabFragment(), "اقتصاد")
        viewPagerAdapter.addFragment(ProfileTabFragment2(), "بورس")
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator((home_page_TL as TabLayout), (viewPager as ViewPager2)) { tab, position ->
            tab.text = viewPagerAdapter.getName(position)
        }.attach()
//        viewPager.addFragment()
    }

}