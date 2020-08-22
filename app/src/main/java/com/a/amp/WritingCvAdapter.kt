package com.a.amp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.writing_cv.view.*
import kotlinx.android.synthetic.main.writing_cv.view.writing_cv_tv_1

class WritingCvAdapter(
    private val list: MutableList<WritingCvDataItem>
) :
    RecyclerView.Adapter<WritingCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainText: TextView = itemView.writing_cv_tv_4
        var userFullName: TextView = itemView.writing_cv_tv_1
        var days: TextView = itemView.writing_cv_tv_2
        var title: TextView = itemView.writing_cv_tv_3
        var id : Int = 0
        init {
            itemView.setOnClickListener{
//                Log.i("baby","hole item clicked")
                it.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToArticleFragment())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView =
            LayoutInflater.from(parent.context).inflate(R.layout.writing_cv, parent, false)
        return MyViewHolder(textView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mainText.text = list[position].text
        holder.userFullName.text = list[position].name
        holder.days.text = list[position].days
        holder.title.text = list[position].title
        holder.id = list[position].id
    }

}
