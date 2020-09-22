package com.a.amp.article.data

import android.app.Application
import android.widget.Toast
import com.a.amp.MyApp
import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.article.apimodel2.ArticleResponse2
import com.a.amp.article.apimodel2.ArticleX
import com.a.amp.core.resource.Resource
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(application: Application) {
    val app = application

    suspend fun fillCommentFromRepo(commentList: MutableList<CommentCvDataItem>) {
        val article = ArticleLocal(app)
        article.fillCommentFromLocal(commentList)
    }

    suspend fun fillRelatedFromRepo(): MutableList<ArticleRelatedCvDataItem> {
        val article = ArticleLocal(app)
        return article.fillRelatedFromLocal()
    }

    suspend fun createArticleFromRepo(
        body: String,
        description: String,
        tagList: List<String>,
        title: String
    ): Resource<ArticleResponse2> {
        val at = ArticleRemote()
        return at.createArticleForServer(body, description, tagList, title)
    }

    suspend fun fillSingleArticleFromRepo(id: String): MutableList<WritingCvDataItem> {
        val art = ArticleRemote()
        val repoResult = art.getSingleArticleBySlug(id)
        if (repoResult.status == Status.SUCCESS && repoResult.code == 200) {
            val unformattedList = repoResult.data?.article
            val formattedList = mutableListOf<ArticleX>()
            unformattedList?.let { formattedList.add(it) }
            val resultList = ArticleEntity.convertToDataItem5(formattedList)
            val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
            for (i in resultList.indices) {
                db.myDao().insertArticles(resultList[i])
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "بروزرسانی انجام شد", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.SUCCESS && repoResult.code != 200) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "خطا", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.ERROR) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show()
            }
        }
        val article = ArticleLocal(app)
        return article.fillSingleFromLocal(id)
    }

    suspend fun syncArticles(): Resource<ArticleResponse> {
        val remote = ArticleRemote()
        return remote.getArticlesFromServer()
    }
}