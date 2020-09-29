package com.a.amp.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.R
import com.a.amp.databinding.RelatedCvBinding
import com.a.amp.home.data.HomeRelatedCvDataItem
import kotlinx.android.synthetic.main.related_cv.view.*

class HomeRelatedCvAdapter(
    var list: MutableList<HomeRelatedCvDataItem>
) :
    RecyclerView.Adapter<HomeRelatedCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: RelatedCvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                try {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment(list[position].id))
                } catch (e: Exception) {
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
        holder.binding.flag = true
        holder.binding.related = list[position]
        holder.binding.saved = list[position].isTag
    }

}
