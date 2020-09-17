package com.a.amp.user.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.core.resource.LoginAction
import com.a.amp.core.resource.Status
import com.a.amp.databinding.FragmentSignupBinding
import com.a.amp.storage.setting
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    private lateinit var setting: setting
    private lateinit var Binding: FragmentSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setting = setting()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignupBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_signup, container, false
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

        loginViewModel.isSigned.observe(viewLifecycleOwner, { isSigned ->
            if (isSigned != null) {
                if (isSigned == LoginAction.LOGIN) {
                    setting.putString("username", signup_et_2.editText?.text.toString())
                    findNavController().navigate(SignupFragmentDirections.actionGlobalHomeFragment())
                } else if (isSigned == LoginAction.WRONG
                    && loginViewModel.result2.value?.errorbody?.source()?.buffer.toString()
                        .substringAfter("\",\"email\":\"").startsWith("is already")
                ) {
                    Toast.makeText(context, "ایمیل قبلا استفاده شده", Toast.LENGTH_SHORT).show()
                    Log.i(
                        "baby",
                        loginViewModel.result2.value?.errorbody?.source()?.buffer.toString()
                    )
                } else if (isSigned == LoginAction.WRONG
                    && loginViewModel.result2.value?.errorbody?.source()?.buffer.toString()
                        .substringAfter("\",\"email\":\"").startsWith("is inv")
                ) {
                    Toast.makeText(context, "ایمیل را درست وارد کنید", Toast.LENGTH_SHORT).show()
                    Log.i(
                        "baby",
                        loginViewModel.result2.value?.errorbody?.source()?.buffer.toString()
                    )
                } else if (isSigned == LoginAction.WRONG
                    && loginViewModel.result2.value?.errorbody?.source()?.buffer.toString()
                        .substringAfter("\"username\":\"").startsWith("is already")
                ) {
                    Toast.makeText(context, "نام کاربری قبلا استفاده شده", Toast.LENGTH_SHORT)
                        .show()
                } else if (isSigned == LoginAction.WRONG
                    && loginViewModel.result2.value?.errorbody?.source()?.buffer.toString()
                        .substringAfter("\"username\":\"").startsWith("is inv")
                ) {
                    Toast.makeText(context, "نام کاربری را درست وارد کنید", Toast.LENGTH_SHORT)
                        .show()
                } else if (isSigned == LoginAction.WRONG) {
                    Toast.makeText(context, "ثبت نام ناموفق", Toast.LENGTH_SHORT).show()
                } else if (isSigned == LoginAction.FAIL) {
                    Toast.makeText(context, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show()
                }
            }
        })

        loginViewModel.result2.observe(viewLifecycleOwner, { result ->
            if (result.status == Status.SUCCESS && result.code == 200) {
                loginViewModel.isSigned.postValue(LoginAction.LOGIN)
            } else if (result.status == Status.SUCCESS && result.code != 200) {
                loginViewModel.isSigned.postValue(LoginAction.WRONG)
            } else if (result.status == Status.ERROR) {
                loginViewModel.isSigned.postValue(LoginAction.FAIL)
            }
        })

        signup_btn_1.setOnClickListener {
            if (isValid()) {
                loginViewModel.authenticate2()
            }
        }
        signup_tv_2.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }


    private fun isValid(): Boolean {
        signup_et_1.error = null
        signup_et_2.error = null
        signup_et_3.error = null
        signup_et_4.error = null
        signup_et_1.isErrorEnabled = false
        signup_et_2.isErrorEnabled = false
        signup_et_3.isErrorEnabled = false
        signup_et_4.isErrorEnabled = false
        if (signup_et_1.editText?.text.toString() == "") {
            signup_et_1.error = "نام را وارد کنید"
            signup_et_1.requestFocus()
            return false
        } else if (signup_et_1.editText?.text.toString().contains('_')) {
            signup_et_1.error = "خط تیره مجاز نیست"
            signup_et_1.requestFocus()
            return false
        } else if (signup_et_2.editText?.text.toString() == "") {
            signup_et_2.error = "ایمیل را وارد کنید"
            signup_et_2.requestFocus()
            return false
        } else if (
            !signup_et_2.editText?.text.toString().contains('@') ||
            !signup_et_2.editText?.text.toString().contains('.')
        ) {
            signup_et_2.error = "فرمت ایمیل اشتباه است"
            signup_et_2.requestFocus()
            return false
        } else if (signup_et_3.editText?.text.toString() == "") {
            signup_et_3.error = "رمز عبور را وارد کنید"
            signup_et_3.requestFocus()
            return false
        } else if (signup_et_3.editText?.text.toString().length < 8 || signup_et_3.editText?.text.toString().length > 20) {
            signup_et_3.error = "رمز عبور باید بین 8 تا 20 کارکتر باشد"
            signup_et_3.requestFocus()
            return false
        } else if (signup_et_4.editText?.text.toString() == "") {
            signup_et_4.error = "تکرار رمز عبور را وارد کنید"
            signup_et_4.requestFocus()
            return false
        } else if (signup_et_3.editText?.text.toString() != signup_et_4.editText?.text.toString()) {
            signup_et_4.error = "تکرار رمز اشتباه است"
            signup_et_4.requestFocus()
            return false
        }
        return true
    }

}