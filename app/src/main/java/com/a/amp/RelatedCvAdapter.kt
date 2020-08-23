package com.a.amp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.related_cv.view.*
import java.lang.IllegalArgumentException

class RelatedCvAdapter(
    private val list: MutableList<RelatedCvDataItem>
) :
    RecyclerView.Adapter<RelatedCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainText: TextView = itemView.related_tv_1
        var userFullName: TextView = itemView.related_tv_2
        var days: TextView = itemView.related_tv_3
        var id : Int = 0
        init {
            itemView.setOnClickListener{
                try {
                    it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
                }catch (E : IllegalArgumentException){
                    it.findNavController().navigate(ArticleFragmentDirections.actionArticleFragmentSelf())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView =
            LayoutInflater.from(parent.context).inflate(R.layout.related_cv, parent, false)
        return MyViewHolder(textView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mainText.text = list[position].text
        holder.userFullName.text = list[position].name
        holder.days.text = list[position].days
        holder.id = list[position].id
    }

}
