package com.a.amp.user.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.amp.MyApp
import com.a.amp.article.apimodel2.Article
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleRepository
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.storage.setting
import com.a.amp.user.data.UserFavEntity
import com.a.amp.user.data.UserRepository
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileListViewModel(application: Application) : AndroidViewModel(application) {
    var writeList = MutableLiveData<MutableList<WritingCvDataItem>>()
    var writeList2 = MutableLiveData<MutableList<WritingCvDataItem>>()
    val app = application
    val isFollowing: MutableLiveData<Boolean> = MutableLiveData()
    var favorited = MutableLiveData<Boolean>()


    init {
        writeList.value = mutableListOf()
        writeList2.value = mutableListOf()
    }

    suspend fun fillWrite(username: String) {
        writeList.postValue(null)
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val repo = UserRepository(app)
        val repoResult = repo.getArticlesByAuthorFromRepo(username)
        if (repoResult.status == Status.SUCCESS && repoResult.code == 200) {
            db.myDao().deleteArticlesByAuthor(username)
            val unformattedList = repoResult.data?.articles
            val formattedList = mutableListOf<Article>()
            unformattedList?.let { formattedList.addAll(it) }
            val resultList = ArticleEntity.convertToDataItem4(formattedList)
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
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val user = UserRepository(app)
        writeList.postValue(user.fillWriteFromRepo(username))
    }

    fun fillWrite2(username: String) {
        writeList2.postValue(null)
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val repo = ArticleRepository(app)
        CoroutineScope(Dispatchers.IO).launch {
            val repoResult = repo.syncArticles()
            if (repoResult.status == Status.SUCCESS && repoResult.code == 200) {
                db.myDao().deleteArticlesByAuthor(username)
                val unformattedList = repoResult.data?.articles
                val formattedList = mutableListOf<Article>()
                unformattedList?.let { formattedList.addAll(it) }
                val resultList = ArticleEntity.convertToDataItem4(formattedList)
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
                    Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            val user = UserRepository(app)
            writeList2.postValue(user.fillLikedFromRepo())
        }
    }

    fun fillWrite3(username: String) {
        writeList2.postValue(null)
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val repo = UserRepository(app)
        CoroutineScope(Dispatchers.IO).launch {
            val repoResult = repo.getFavoriteByUsername(username)
            if (repoResult.status == Status.SUCCESS && repoResult.code == 200) {
                val unformattedList = repoResult.data?.articles
                val formattedList = mutableListOf<Article>()
                unformattedList?.let { formattedList.addAll(it) }
                val resultList = ArticleEntity.convertToDataItem4(formattedList)
                for (i in 0 until resultList.size) {
                    db.myDao().insertArticles(resultList[i])
                }
                val resultList2 = UserFavEntity.convertToDataItem(resultList, username)
                for (i in resultList2.indices) {
                    db.myDao().insertUserFavs(resultList2[i])
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
                    Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            val user = UserRepository(app)
            writeList2.postValue(user.fillOthersLikedFromRepo(username))
        }
    }

    fun followOtherProfile(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = UserRepository(application = Application())
                .followResult(
                    username = username,
                    email = setting().getString("id")
                )
            isFollowing.postValue(
                result.data?.profile?.following
            )
        }
        Log.i("tag", isFollowing.toString())
    }

    fun unFollowOtherProfile(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = UserRepository(application = Application()).unFollowResult(
                username = username
            )
            isFollowing.postValue(
                result.data?.profile?.following
            )
        }
    }

    fun getProfile(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = UserRepository(application = Application()).getUserProfile(
                username = username
            )
            isFollowing.postValue(result.data?.profile?.following)
        }
    }

    fun favoriteArticle(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favArt = ArticleRepository(app).favoriteArticle(slug)
            if (favArt.code == 200){favorited.postValue(true)}
        }

    }

    fun unFavoriteArticle(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favArt = ArticleRepository(app).unFavoriteArticle(slug)
            if (favArt.code == 200){favorited.postValue(false)}
        }
    }
}