package com.a.amp.article.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.amp.MyApp
import com.a.amp.article.apimodel2.ArticleResponse2
import com.a.amp.article.apimodel2.ArticleResponse4
import com.a.amp.article.data.ArticleRepository
import com.a.amp.core.resource.Resource
import com.a.amp.database.AppDataBase
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteViewModel(application: Application) : AndroidViewModel(application) {
    var tagList = MutableLiveData<MutableList<String>>()
    val slug = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val result = MutableLiveData<Resource<ArticleResponse2>>()
    val editResult = MutableLiveData<Resource<ArticleResponse4>>()
    val app = application
    var resultArticle = MutableLiveData<MutableList<WritingCvDataItem>>()

    init {
        resultArticle.value = mutableListOf()
        tagList.value = mutableListOf()
    }

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
                    tagList = tagList,
                    title = title.value.toString()
                )
            )
        }
    }


    suspend fun getArticleAndTags(slug: String) {
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val article = ArticleRepository(app)
        val res = article.fillSingleArticleWithCommentsFromRepo(slug)
        resultArticle.postValue(res[0] as MutableList<WritingCvDataItem>)
        tagList.postValue(res[2] as MutableList<String>)
    }

}