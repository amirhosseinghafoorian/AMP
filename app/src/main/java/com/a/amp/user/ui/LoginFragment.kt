package com.a.amp.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.decorView?.systemUiVisibility = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.isLogin.observe(viewLifecycleOwner, { isLogin ->
            if (isLogin) {
                findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
            }
        })
        login_intro_4.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        login_btn_1.setOnClickListener {
            loginViewModel.authenticate(
                login_et_1.editText?.text.toString(),
                login_et_2.editText?.text.toString()
            )
            if (loginViewModel.isLogin.value == false) {
                Toast.makeText(context, "username or password wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}