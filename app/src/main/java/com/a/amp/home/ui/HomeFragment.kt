package com.a.amp.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.setting
import kotlinx.android.synthetic.main.fragment_home.*

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

        loginViewModel.fillSummary()
        loginViewModel.fillRelated()

        home_appbar_end_icon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
        home_appbar_start_icon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteFragment())
        }
        home_appbar_end_icon_power.setOnClickListener {
            setting.remove("username")
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAuthenticate())
        }
    }
}