package com.a.amp.user.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.amp.user.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var isLogin: MutableLiveData<Boolean> = MutableLiveData()
    var isSigned: MutableLiveData<Boolean> = MutableLiveData()

    fun authenticate(user: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (user != "") {
                val ur = UserRepository(application = Application())
                isLogin.postValue(null)
                val result = ur.loginResult(user, pass)
                if (result) {
                    isLogin.postValue(true)
                } else if (!result) {
                    isLogin.postValue(false)
                }
            }
        }
    }

    fun authenticate2(user: String, pass: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (user != "") {
                val ur = UserRepository(application = Application())
                isSigned.postValue(null)
                val result = ur.signUpResult(user, pass, email)
                if (result) {
                    isSigned.postValue(true)
                } else if (!result) {
                    isSigned.postValue(false)
                }
            }
        }
    }
}