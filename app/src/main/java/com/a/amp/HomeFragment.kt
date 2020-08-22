package com.a.amp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_appbar_end_icon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
        home_appbar_start_icon.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteFragment())
        }
    }
}