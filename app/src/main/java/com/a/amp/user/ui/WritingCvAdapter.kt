package com.a.amp.user.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.MyApp
import com.a.amp.R
import com.a.amp.article.data.BookmarkEntity
import com.a.amp.database.AppDataBase
import com.a.amp.databinding.WritingCvBinding
import com.a.amp.user.data.MoreClickListener
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.android.synthetic.main.writing_cv.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WritingCvAdapter(
    var list: MutableList<WritingCvDataItem>,
    var clickListener: MoreClickListener? = null,
    var current: String?,
    var username: String?
//,var clickcallback:(id : Int) -> Unit
) :
    RecyclerView.Adapter<WritingCvAdapter.MyViewHolder>() {

    @Suppress("DEPRECATION")
    inner class MyViewHolder(binding: WritingCvBinding) : RecyclerView.ViewHolder(binding.root) {
//        var mainText: TextView = itemView.writing_cv_tv_4
//        var userFullName: TextView = itemView.writing_cv_tv_1
//        var days: TextView = itemView.writing_cv_tv_2
//        var title: TextView = itemView.writing_cv_tv_3
//        var id : Int = 0

        var binding: WritingCvBinding = binding

        init {
            val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
            CoroutineScope(Dispatchers.IO).launch {
                for (i in 0 until list.size) {
                    val a = db.myDao().getBookmark(list[i].id)
                    if (a.isNotEmpty()) {
                        list[i].isTag = true
                    }
                }
            }

            itemView.setOnClickListener {
                it.findNavController()
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToArticleFragment(list[position].id))
            }
            if (current != username) {
                itemView.writing_cv_ic_more.visibility = View.GONE
            }
            itemView.writing_cv_ic_more.setOnClickListener {
//                clickCallBack.invoke(2)
                clickListener?.onClick(list[layoutPosition].id, layoutPosition, "more")
            }
            itemView.writing_cv_ic_bookmark.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (list[position].isTag) {
                        db.myDao().deleteBookmark(list[position].id)
                    }else{
                        db.myDao().insertBookmarks(BookmarkEntity( list[position].id))
                    }
                    withContext(Dispatchers.Main){
                        list[position].isTag = list[position].isTag.not()
                        notifyItemChanged(position)
                    }
                }
            }

            itemView.writing_cv_icon_like.setOnClickListener {
                if (list[position].isFav) {
                    clickListener?.onClick(list[layoutPosition].id, layoutPosition, "unlike")
                    list[position].isFav = list[position].isFav.not()
                    list[position].FavCont -= 1
                    notifyItemChanged(position)
                } else {
                    clickListener?.onClick(list[layoutPosition].id, layoutPosition, "like")
                    list[position].isFav = list[position].isFav.not()
                    list[position].FavCont += 1
                    notifyItemChanged(position)

                }

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: WritingCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.writing_cv, parent, false
        )
//        val textView =
//            LayoutInflater.from(parent.context).inflate(R.layout.writing_cv, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.mainText.text = list[position].text
//        holder.userFullName.text = list[position].name
//        holder.days.text = list[position].days
//        holder.title.text = list[position].title
//        holder.id = list[position].id
        holder.binding.write = list[position]
        holder.binding.saved = list[position].isTag
        holder.binding.liked = list[position].isFav
//        holder.binding.write.cont = list[position].cont
    }

}
