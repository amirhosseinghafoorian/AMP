package com.a.amp.article.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.R
import com.a.amp.article.data.CommentCvDataItem
import com.a.amp.databinding.ComentCvBinding

class CommentCvAdapter(
    private val list: MutableList<CommentCvDataItem>
) :
    RecyclerView.Adapter<CommentCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ComentCvBinding) : RecyclerView.ViewHolder(binding.root) {
        //        var mainText: TextView = itemView.comment_tv_2
//        var userFullName: TextView = itemView.comment_tv_1
//        var id : Int = 0
        var binding: ComentCvBinding = binding

        init {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ComentCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.coment_cv, parent, false
        )
//        val textView =
//            LayoutInflater.from(parent.context).inflate(R.layout.coment_cv, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.mainText.text = list[position].text
//        holder.userFullName.text = list[position].name
//        holder.id = list[position].id
        holder.binding.comment = list[position]
    }

}
