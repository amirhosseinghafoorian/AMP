package com.a.amp.article.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.amp.article.apimodel2.ArticleResponse2
import com.a.amp.article.data.ArticleRepository
import com.a.amp.core.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteViewModel(application: Application) : AndroidViewModel(application) {
    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val result = MutableLiveData<Resource<ArticleResponse2>>()
    val app = application

    fun create(tagList: MutableList<String>) {
        val ar = ArticleRepository(app)
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(Resource.loading(null))
            result.postValue(
                ar.createArticleFromRepo(
                    body = body.value.toString(),
                    description = description.value.toString(),
                    tagList,
                    title = title.value.toString()
                )
            )
        }
    }

}