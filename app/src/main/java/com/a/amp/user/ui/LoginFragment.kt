package com.a.amp.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.core.resource.LoginAction
import com.a.amp.core.resource.Status
import com.a.amp.databinding.FragmentLoginBinding
import com.a.amp.storage.setting
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var setting: setting
    private lateinit var Binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.decorView?.systemUiVisibility = 0
        setting = setting()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        Binding = binding
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val loginViewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)

        Binding.also {
            it.vm = loginViewModel
            it.lifecycleOwner = this
        }

        loginViewModel.isLogin.observe(viewLifecycleOwner, { isLogin ->
            if (isLogin != null) {
                if (isLogin == LoginAction.LOGIN) {
                    setting.putString("id", login_et_1.editText?.text.toString())
                    setting.putString(
                        "username",
                        loginViewModel.result.value?.data?.user?.username.toString()
                    )
                    findNavController().navigate(LoginFragmentDirections.actionGlobalHomeFragment())
                } else if (isLogin == LoginAction.WRONG) {
                    Toast.makeText(context, "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT)
                        .show()
                } else if (isLogin == LoginAction.FAIL) {
                    Toast.makeText(context, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        loginViewModel.result.observe(viewLifecycleOwner, { result ->
            if (result.status == Status.SUCCESS && result.code == 200) {
                loginViewModel.isLogin.postValue(LoginAction.LOGIN)
            } else if (result.status == Status.SUCCESS && result.code != 200) {
                loginViewModel.isLogin.postValue(LoginAction.WRONG)
            } else if (result.status == Status.ERROR) {
                loginViewModel.isLogin.postValue(LoginAction.FAIL)
            }
        })

        login_intro_4.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
        login_btn_1.setOnClickListener {
            if (isValid()) {
                loginViewModel.authenticate(
//                    login_et_1.editText?.text.toString(),
//                    login_et_2.editText?.text.toString()
                )
            }
        }
    }

    private fun isValid(): Boolean {
        login_et_1.error = null
        login_et_2.error = null
        login_et_1.isErrorEnabled = false
        login_et_2.isErrorEnabled = false
        if (login_et_1.editText?.text.toString() == "") {
            login_et_1.error = "نام کاربری را وارد کنید"
            login_et_1.requestFocus()
            return false
        } else if (
            !login_et_1.editText?.text.toString().contains('@') ||
            !login_et_1.editText?.text.toString().contains('.')
        ) {
            login_et_1.error = "فرمت نام کاربری اشتباه است"
            login_et_1.requestFocus()
            return false
        } else if (login_et_2.editText?.text.toString() == "") {
            login_et_2.error = "رمز عبور را وارد کنید"
            login_et_2.requestFocus()
            return false
        } else if (login_et_2.editText?.text.toString().length < 8 || login_et_2.editText?.text.toString().length > 20) {
            login_et_2.error = "رمز عبور باید بین 8 تا 20 کارکتر باشد"
            login_et_2.requestFocus()
            return false
        }
        return true
    }
}