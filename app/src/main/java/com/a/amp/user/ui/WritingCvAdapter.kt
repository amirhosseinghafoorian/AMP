package com.a.amp.user.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.R
import com.a.amp.databinding.WritingCvBinding
import com.a.amp.user.data.MoreClickListner
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.android.synthetic.main.writing_cv.view.*

class WritingCvAdapter(
    private val list: MutableList<WritingCvDataItem>,
    var clickListener: MoreClickListner? = null
//,var clickcallback:(id : Int) -> Unit
) :
    RecyclerView.Adapter<WritingCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: WritingCvBinding) : RecyclerView.ViewHolder(binding.root) {
//        var mainText: TextView = itemView.writing_cv_tv_4
//        var userFullName: TextView = itemView.writing_cv_tv_1
//        var days: TextView = itemView.writing_cv_tv_2
//        var title: TextView = itemView.writing_cv_tv_3
//        var id : Int = 0

        var binding: WritingCvBinding = binding

        init {
            itemView.setOnClickListener {
                it.findNavController()
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToArticleFragment())
            }
            itemView.writing_cv_ic_more.setOnClickListener {
//                clickcallback.invoke(2)
                clickListener?.onClick(list[layoutPosition].id, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: WritingCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),
            R.layout.writing_cv, parent, false
        )
//        val textView =
//            LayoutInflater.from(parent.context).inflate(R.layout.writing_cv, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.mainText.text = list[position].text
//        holder.userFullName.text = list[position].name
//        holder.days.text = list[position].days
//        holder.title.text = list[position].title
//        holder.id = list[position].id
        holder.binding.write = list[position]
    }

}
