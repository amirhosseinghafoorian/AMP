package com.a.amp.user.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.amp.core.resource.LoginAction
import com.a.amp.core.resource.Resource
import com.a.amp.user.apimodel1.LoginResponse
import com.a.amp.user.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var isLogin: MutableLiveData<LoginAction> = MutableLiveData()
    var isSigned: MutableLiveData<Boolean> = MutableLiveData()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val result = MutableLiveData<Resource<LoginResponse>>()

    fun authenticate() {
        viewModelScope.launch(Dispatchers.IO) {
            if (username.value != "") {
                val ur = UserRepository(application = Application())
                isLogin.postValue(null)
                result.postValue(Resource.loading(null))
                result.postValue(
                    ur.loginResult(
                        username.value.toString(),
                        password.value.toString()
                    )
                )
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