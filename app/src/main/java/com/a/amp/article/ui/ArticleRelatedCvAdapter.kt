package com.a.amp.article.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.R
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.databinding.RelatedCvBinding
import com.a.amp.home.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.related_cv.view.*

class ArticleRelatedCvAdapter(
    private val list: MutableList<ArticleRelatedCvDataItem>
) :
    RecyclerView.Adapter<ArticleRelatedCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: RelatedCvBinding) : RecyclerView.ViewHolder(binding.root) {
//        var mainText: TextView = itemView.related_tv_1
//        var userFullName: TextView = itemView.related_tv_2
//        var days: TextView = itemView.related_tv_3
//        var id : Int = 0

        var binding: RelatedCvBinding = binding

        init {
            itemView.setOnClickListener {
                try {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment(list[position].id))
                } catch (E: IllegalArgumentException) {
                    it.findNavController()
                        .navigate(ArticleFragmentDirections.actionArticleFragmentSelf(list[position].id))
                }
            }
            itemView.related_bookmark.setOnClickListener {
                list[position].isTag = list[position].isTag.not()
                notifyItemChanged(position)
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
//        holder.mainText.text = list[position].text
//        holder.userFullName.text = list[position].name
//        holder.days.text = list[position].days
//        holder.id = list[position].id
        holder.binding.flag = false
        holder.binding.related2 = list[position]
        holder.binding.saved = list[position].isTag
    }

}
