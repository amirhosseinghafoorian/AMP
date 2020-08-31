package com.a.amp.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.R
import com.a.amp.article.ui.ArticleFragmentDirections
import com.a.amp.databinding.RelatedCvBinding
import com.a.amp.home.data.HomeRelatedCvDataItem

class HomeRelatedCvAdapter(
    private val list: MutableList<HomeRelatedCvDataItem>
) :
    RecyclerView.Adapter<HomeRelatedCvAdapter.MyViewHolder>() {

    class MyViewHolder(binding: RelatedCvBinding) : RecyclerView.ViewHolder(binding.root) {

        var binding: RelatedCvBinding = binding

        init {
            itemView.setOnClickListener {
                try {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
                } catch (E: IllegalArgumentException) {
                    it.findNavController()
                        .navigate(ArticleFragmentDirections.actionArticleFragmentSelf())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: RelatedCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
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
    }

}
