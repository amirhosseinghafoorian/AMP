package com.a.amp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signup_btn_1.setOnClickListener{
            findNavController().navigate(SignupFragmentDirections.actionGlobalHomeFragment())
        }
        signup_tv_2.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

}