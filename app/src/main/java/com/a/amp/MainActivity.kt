package com.a.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_tag.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val list = mutableListOf<WritingCvDataItem>()
//        val list2 = mutableListOf<CommentCvDataItem>()

//        repeat(10) {
//            list.add(WritingCvDataItem(" سه خط مقاله : $it"," دو خط مقاله : $it",
//                " نام کاربر : $it" , "$it روز پیش ",0))

//            list2.add(
//                CommentCvDataItem(" دو خط مقاله : $it",
//                " نام کاربر : $it",0))
//        }

//        val myAdapter2 = CommentCvAdapter(list2)
//        val myAdapter = WritingCvAdapter(list)

//        article_page_recycle_2.apply {
//            adapter = myAdapter2
//            setHasFixedSize(true)
//        }

//        profile_recycler.apply {
//            adapter = myAdapter
//            setHasFixedSize(true)
//        }
    }
}