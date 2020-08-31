package com.a.amp.user.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var isLogin = MutableLiveData(false)

    fun authenticate(user: String, pass: String) {
        if (user == pass && user != "") {
            isLogin.value = true
        }
    }
}