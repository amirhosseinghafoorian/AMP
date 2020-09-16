package com.a.amp.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.storage.setting
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    private lateinit var setting: setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setting = setting()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.isSigned.observe(viewLifecycleOwner, { isSigned ->
            if (isSigned != null) {
                if (isSigned) {
                    setting.putString("username", signup_et_2.editText?.text.toString())
                    findNavController().navigate(SignupFragmentDirections.actionGlobalHomeFragment())
                } else if (!isSigned) {
                    Toast.makeText(context, "ثبت نام ناموفق", Toast.LENGTH_SHORT).show()
                }
            }
        })

        signup_btn_1.setOnClickListener {
            if (isValid()) {
                loginViewModel.authenticate2(
                    signup_et_1.editText?.text.toString(),
                    signup_et_3.editText?.text.toString(),
                    signup_et_2.editText?.text.toString()
                )
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