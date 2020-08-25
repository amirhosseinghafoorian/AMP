package com.a.amp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.databinding.WritingCvBinding

class WritingCvAdapter(
    private val list: MutableList<WritingCvDataItem>
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
//                Log.i("baby","hole item clicked")
                it.findNavController()
                    .navigate(ProfileFragmentDirections.actionProfileFragmentToArticleFragment())
            }
//            itemView.writing_cv_ic_more.setOnClickListener {
//                val bottonSheetDialog = BottomSheetDialog(requireContext())
//                val  view = layoutInflater.inflate(R.layout.bottom_sheet,null)
//
//                bottonSheetDialog.setContentView(view)
//
//                bottonSheetDialog.show()
//            }
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
