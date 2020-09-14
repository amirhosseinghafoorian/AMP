package com.a.amp.user.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.amp.user.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var isLogin = MutableLiveData(false)

    fun authenticate(user: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (user != "") {
                val ur = UserRepository(application = Application())
                val result = ur.loginResult(user, pass)
                if (result) {
                    isLogin.value = true
                }
            }
        }
    }
}