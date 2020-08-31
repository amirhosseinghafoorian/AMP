package com.a.amp.article.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.a.amp.R
import kotlinx.android.synthetic.main.fragment_write.*

class WriteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_write, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        write_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        write_bt_3.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

}