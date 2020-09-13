package com.a.amp.article.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.a.amp.AppDataBase
import com.a.amp.user.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleLocal(application: Application) {

    val app = application
    val db = AppDataBase.buildDatabase(context = app)

    fun fillCommentFromLocal(CommentList: MutableList<CommentCvDataItem>) {
        GlobalScope.launch(Dispatchers.IO) {
            db.myDao().insertUsers(UserEntity(101, "alireza"))
            db.myDao().insertArticles(ArticleEntity(201, 101, "test article", "here we go"))
            repeat(1) {
                db.myDao().insertComments(CommentEntity(301, 201, "my sample comment"))
//            CommentList.value?.add(
//                CommentCvDataItem(
//                    " دو خط مقاله : $it",
//                    " نام کاربر : $it", 0
//                )
//            )
            }
            CommentList.addAll(CommentEntity.convertToDataItem(db.myDao().getComments()))
        }

    }

    fun fillRelatedFromLocal(relatedList: MutableLiveData<MutableList<ArticleRelatedCvDataItem>>) {
        repeat(10) {
            relatedList.value?.add(
                ArticleRelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0, false
                )
            )
        }
    }
}