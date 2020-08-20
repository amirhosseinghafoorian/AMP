package com.a.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.home_page.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signup)
//
//        val list = mutableListOf<RelatedCvDataItem>()
//
//        repeat(5) {
//            list.add(RelatedCvDataItem(" دو خط مقاله : $it",
//                " نام کاربر : $it" , "$it روز پیش "))
//        }
//
//        val myAdapter = RelatedCvAdapter(list)
//        val myAdapter2 = SummaryCvAdapter(list)
//
//
//        home_page_recycle_2.apply {
//            adapter = myAdapter2
//            setHasFixedSize(true)
//        }
//
//        home_page_recycle_1.apply {
//            adapter = myAdapter
//            setHasFixedSize(true)
//        }
    }
}