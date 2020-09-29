package com.a.amp.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.a.amp.MyApp
import com.a.amp.R
import com.a.amp.database.AppDataBase
import com.a.amp.databinding.SummaryCvBinding
import com.a.amp.home.data.HomeRelatedCvDataItem
import kotlinx.android.synthetic.main.summary_cv.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeSummaryCvAdapter(
    var list: MutableList<HomeRelatedCvDataItem>
) :
    RecyclerView.Adapter<HomeSummaryCvAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: SummaryCvBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SummaryCvBinding = binding

        //        var mainText: TextView = itemView.summary_tv_3
//        var userFullName: TextView = itemView.summary_tv_1
//        var days: TextView = itemView.summary_tv_2
//        var id : Int = 0
        init {
            val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
            CoroutineScope(Dispatchers.IO).launch {
                for (i in 0 until list.size) {
                    val a = db.myDao().getBookmark(list[i].id)
                    if (a.isNotEmpty()) {
                        list[i].isTag = true
                    }
                }
            }
            itemView.setOnClickListener {
                try {
                    it.findNavController()
                        .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment(list[position].id))
                } catch (e: Exception) {
                }
            }
            itemView.summary_bookmark.setOnClickListener {
                list[position].isTag = list[position].isTag.not()
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: SummaryCvBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.summary_cv, parent, false
        )
//        val textView =
//            LayoutInflater.from(parent.context).inflate(R.layout.summary_cv, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.mainText.text = list[position].text
//        holder.userFullName.text = list[position].name
//        holder.days.text = list[position].days
//        holder.id = list[position].id
        holder.binding.flag = true
        holder.binding.summary = list[position]
        holder.binding.saved = list[position].isTag
    }

}
