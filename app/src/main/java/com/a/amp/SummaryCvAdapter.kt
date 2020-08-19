package com.a.amp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.related_cv.view.*
import kotlinx.android.synthetic.main.summary_cv.view.*

class SummaryCvAdapter(
    val list: MutableList<RelatedCvDataItem>
) :
    RecyclerView.Adapter<SummaryCvAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainText: TextView = itemView.summary_tv_3
        var userFullName: TextView = itemView.summary_tv_1
        var days: TextView = itemView.summary_tv_2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView =
            LayoutInflater.from(parent.context).inflate(R.layout.summary_cv, parent, false)
        return MyViewHolder(textView)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mainText.text = list[position].text
        holder.userFullName.text = list[position].name
        holder.days.text = list[position].days
    }



}
