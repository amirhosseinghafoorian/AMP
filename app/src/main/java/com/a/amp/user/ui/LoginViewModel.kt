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
    var isSigned: MutableLiveData<LoginAction> = MutableLiveData()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val result = MutableLiveData<Resource<LoginResponse>>()
    val result2 = MutableLiveData<Resource<LoginResponse>>()

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

    fun authenticate2() {
        viewModelScope.launch(Dispatchers.IO) {
            if (username.value != "") {
                val ur = UserRepository(application = Application())
                isSigned.postValue(null)
                result2.postValue(Resource.loading(null))
                result2.postValue(
                    ur.signUpResult(
                        name.value.toString(),
                        password.value.toString(),
                        username.value.toString()
                    )
                )

            }
        }
    }
}