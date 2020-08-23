package com.a.amp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.coment_cv.view.*
import kotlinx.android.synthetic.main.related_cv.view.*

class CommentCvAdapter(
    private val list: MutableList<CommentCvDataItem>
) :
    RecyclerView.Adapter<CommentCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainText: TextView = itemView.comment_tv_2
        var userFullName: TextView = itemView.comment_tv_1
        var id : Int = 0
        init {
            itemView.setOnClickListener{
                
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView =
            LayoutInflater.from(parent.context).inflate(R.layout.coment_cv, parent, false)
        return MyViewHolder(textView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mainText.text = list[position].text
        holder.userFullName.text = list[position].name
        holder.id = list[position].id
    }

}
