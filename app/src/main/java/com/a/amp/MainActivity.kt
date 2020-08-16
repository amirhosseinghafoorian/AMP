package com.a.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.frag_login.*
import kotlinx.android.synthetic.main.fragment_signup.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//        login_box_3.setOnClickListener(){
//            var user1 = userName.text.toString()
//            var pass1 = passWord.text.toString()
//            var fragmentManager = supportFragmentManager
//            fragmentManager.beginTransaction().replace(R.id.frameLayout, frag2).addToBackStack("frag2").commit()
//        }
//
//        signup_box_5.setOnClickListener(){
//            var fragmentManager = supportFragmentManager
//            fragmentManager.beginTransaction().replace(R.id.frag1).
//        }

    }
}