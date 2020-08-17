package com.a.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.signup.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test_fr = signup_fr()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, test_fr)
        fragmentTransaction.addToBackStack("fr1")
        fragmentTransaction.commit()
    }

//    private fun loadFragment(fragment: Fragment) {
//        // create a FragmentManager
//        val fm = supportFragmentManager
//        // create a FragmentTransaction to begin the transaction and replace the Fragment
//        val fragmentTransaction = fm.beginTransaction()
//        // replace the FrameLayout with new Fragment
//        fragmentTransaction.replace(R.id.fragment, fragment)
//        fragmentTransaction.commit() // save the changes
//    }
}