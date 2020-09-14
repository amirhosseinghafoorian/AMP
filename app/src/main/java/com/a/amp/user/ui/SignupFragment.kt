package com.a.amp.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signup_btn_1.setOnClickListener {
            if (isValid()) {
                findNavController().navigate(SignupFragmentDirections.actionGlobalHomeFragment())
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
        if (signup_et_1.editText?.text.toString() == "") {
            signup_et_1.error = "نام را وارد کنید"
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