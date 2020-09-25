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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepository(application: Application) {
    val app = application

    suspend fun fillRelatedFromRepo(): MutableList<ArticleRelatedCvDataItem> {
        val article = ArticleLocal(app)
        return article.fillRelatedFromLocal()
    }

    suspend fun getArticleTag(slug: String): MutableList<String> {
        val tagList = ArticleLocal(app)
        return  tagList.fillTagFromLocal(slug)
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

    suspend fun editArticleFromRepo(
        body: String,
        description: String,
        tagList: List<String>,
        title: String,
        slug: String
    ): Resource<ArticleResponse4> {
        val at = ArticleRemote()
        return at.editArticleForServer(body, description, tagList, title, slug)
    }

    suspend fun favoriteArticle(slug: String): Resource<ArticleResponse5> {
        val remote = ArticleRemote()
        return remote.favoriteArticleForServer(slug)
    }

    suspend fun unFavoriteArticle(slug: String): Resource<ArticleResponse5> {
        val remote = ArticleRemote()
        return remote.unFavoriteArticleForServer(slug)
    }


    suspend fun fillSingleArticleWithCommentsFromRepo(slug: String): MutableList<Any> {
        val returnList = mutableListOf<Any>()
        val art = ArticleRemote()
        val repoResult = art.getSingleArticleBySlug(slug)
        val repoResult2 = art.getSingleArticleCommentsFromServer(slug)

        if (repoResult.status == Status.SUCCESS &&
            repoResult.code == 200 &&
            repoResult2.status == Status.SUCCESS &&
            repoResult2.code == 200
        ) {

            val unformattedList = repoResult.data?.article
            val formattedList = mutableListOf<ArticleX>()
            unformattedList?.let { formattedList.add(it) }
            val resultList = ArticleEntity.convertToDataItem5(formattedList)
            val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
            for (i in resultList.indices) {
                db.myDao().insertArticles(resultList[i])
            }

            val unformattedList2 = repoResult2.data?.comments
            val formattedList2 =
                unformattedList2?.let { CommentEntity.convertToDataItem2(it, slug) }
            for (i in 0 until formattedList2?.size!!) {
                db.myDao().insertComments(formattedList2[i])
            }

            val unformattedList3 = repoResult.data?.article?.tagList
            val formattedList3 = unformattedList3?.let { TagEntity.convertToDataItem(it, slug) }
            for (i in 0 until formattedList3?.size!!) {
                db.myDao().insertTags(formattedList3[i])
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
        returnList.add(article.fillSingleArticleFromLocal(slug))
        returnList.add(article.fillCommentFromLocal(slug))
        returnList.add(article.fillTagFromLocal(slug))
        return returnList
    }

    suspend fun syncArticles(): Resource<ArticleResponse> {
        val remote = ArticleRemote()
        return remote.getArticlesFromServer()
    }

    suspend fun syncFeed(): Resource<ArticleResponse> {
        val remote = ArticleRemote()
        return remote.getFeedFromServer()
    }
}