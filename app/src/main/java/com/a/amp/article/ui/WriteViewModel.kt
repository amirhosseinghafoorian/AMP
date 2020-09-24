package com.a.amp.article.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.amp.MyApp
import com.a.amp.article.apimodel2.Article
import com.a.amp.article.apimodel2.ArticleResponse2
import com.a.amp.article.apimodel2.ArticleResponse4
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleRepository
import com.a.amp.core.resource.Resource
import com.a.amp.database.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteViewModel(application: Application) : AndroidViewModel(application) {
    val slug = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val result = MutableLiveData<Resource<ArticleResponse2>>()
    val editResult = MutableLiveData<Resource<ArticleResponse4>>()
    val app = application
    var resultArticle: MutableLiveData<List<ArticleEntity>> = MutableLiveData()

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

    fun edit(tagList: MutableList<String>, slug: String) {
        val ar = ArticleRepository(app)
        viewModelScope.launch(Dispatchers.IO) {
            editResult.postValue(Resource.loading(null))
            editResult.postValue(
                ar.editArticleFromRepo(
                    slug = slug,
                    body = body.value.toString(),
                    description = description.value.toString(),
                    tagList,
                    title = title.value.toString()
                )
            )
        }
    }


    fun getArticle(slug: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
            resultArticle.postValue(db.myDao().getSingleArticleById(slug))
        }

    }

}