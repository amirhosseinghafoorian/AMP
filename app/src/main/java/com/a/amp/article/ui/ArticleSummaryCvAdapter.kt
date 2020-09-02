package com.a.amp.article.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.R
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.databinding.SummaryCvBinding
import com.a.amp.home.ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.summary_cv.view.*


class ArticleSummaryCvAdapter(
    private val list: MutableList<ArticleRelatedCvDataItem>
) :
    RecyclerView.Adapter<ArticleSummaryCvAdapter.MyViewHolder>() {


    inner class MyViewHolder(binding: SummaryCvBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SummaryCvBinding = binding

        //        var mainText: TextView = itemView.summary_tv_3
//        var userFullName: TextView = itemView.summary_tv_1
//        var days: TextView = itemView.summary_tv_2
//        var id : Int = 0
        init {
            itemView.setOnClickListener {
                try {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
                } catch (E: IllegalArgumentException) {
                    it.findNavController()
                        .navigate(TagFragmentDirections.actionTagFragmentToArticleFragment())
                }
            }

            itemView.summary_bookmark.setOnClickListener {
                list[position].isTag = list[position].isTag.not()
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: SummaryCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.summary_cv, parent, false
        )
//        val textView =
//            LayoutInflater.from(parent.context).inflate(R.layout.summary_cv, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.mainText.text = list[position].text
//        holder.userFullName.text = list[position].name
//        holder.days.text = list[position].days
//        holder.id = list[position].id

        holder.binding.flag = false
        holder.binding.summary2 = list[position]
        holder.binding.saved = list[position].isTag
    }

}