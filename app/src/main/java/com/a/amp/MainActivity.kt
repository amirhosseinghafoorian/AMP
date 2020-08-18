package com.a.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.article.*
import kotlinx.android.synthetic.main.home_page.*

class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<MyDataItem>()

    private val myAdapter = MyAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        home_page_recycle_1.apply {
            repeat(5) {
                list.add(MyDataItem("First Item $it"))
            }
            adapter = myAdapter
            setHasFixedSize(true)
        }
    }
}