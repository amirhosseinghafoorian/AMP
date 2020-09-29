package com.a.amp.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.MyApp
import com.a.amp.R
import com.a.amp.article.data.BookmarkEntity
import com.a.amp.database.AppDataBase
import com.a.amp.databinding.RelatedCvBinding
import com.a.amp.home.data.HomeRelatedCvDataItem
import kotlinx.android.synthetic.main.related_cv.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeRelatedCvAdapter(
    var list: MutableList<HomeRelatedCvDataItem>
) :
    RecyclerView.Adapter<HomeRelatedCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: RelatedCvBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
                try {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment(list[position].id))
                } catch (e: Exception) {
                }
            }
            itemView.related_bookmark.setOnClickListener {
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RelatedCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.related_cv, parent, false
        )
//        val textView =
//            LayoutInflater.from(parent.context).inflate(R.layout.related_cv, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.flag = true
        holder.binding.related = list[position]
        holder.binding.saved = list[position].isTag
    }

}
