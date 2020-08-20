package com.a.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_article)

        val list = mutableListOf<RelatedCvDataItem>()
        val list2 = mutableListOf<CommentCvDataItem>()

        repeat(5) {
            list.add(RelatedCvDataItem(" دو خط مقاله : $it",
                " نام کاربر : $it" , "$it روز پیش ",0))

            list2.add(
                CommentCvDataItem(" دو خط مقاله : $it",
                " نام کاربر : $it",0))
        }

        val myAdapter = RelatedCvAdapter(list)
        val myAdapter2 = CommentCvAdapter(list2)


        article_page_recycle_2.apply {
            adapter = myAdapter2
            setHasFixedSize(true)
        }

        article_page_recycle_1.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }
    }
}