package com.a.amp.home.data

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.a.amp.core.resource.Resource
import com.a.amp.core.safeApiCall
import com.a.amp.home.apimodel.tagModel
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRemote {

    suspend fun getTagFromServer(): Resource<tagModel>{
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return getTag(auth)
    }

    suspend fun  getTag(authApi: AuthApi): Resource<tagModel>{
        return safeApiCall {
            authApi.getAllTags()
        }
    }


//    suspend fun getarticle () {
//        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
//        return getArticle(auth)
//    }
//
//    private suspend fun getArticle(authApi: AuthApi){
//
//            val reesult = authApi.getAllArt()
//
//        Log.i("tag", reesult.body().toString())
//    }


}