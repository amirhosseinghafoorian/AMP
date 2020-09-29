package com.a.amp.article.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.R
import com.a.amp.article.data.CommentCvDataItem
import com.a.amp.databinding.ComentCvBinding

class CommentCvAdapter(
    var list: MutableList<CommentCvDataItem>
) :
    RecyclerView.Adapter<CommentCvAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: ComentCvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ComentCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.coment_cv, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.comment = list[position]
    }

}
