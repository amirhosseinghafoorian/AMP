package com.a.amp.article.ui

import android.graphics.Color
import android.nfc.Tag
import android.os.Bundle
import android.provider.CalendarContract
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.article.data.ArticleRemote
import com.a.amp.core.resource.Resource
import com.a.amp.databinding.FragmentWriteBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_write.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteFragment : Fragment() {

    val tagList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWriteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_write, container, false
        )
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        write_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        write_bt_4.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        addTag.setOnClickListener {
            addTag()
        }

        write_bt_4.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val crPost = ArticleRemote()
                val createPost = crPost.createArticleForServer(
                    title = write_tl_1.text.toString(),
                    body = write_tl_2.text.toString(),
                    description = write_tl_3.text.toString(),
                    tagList = tagList
                )
            }
        }



    }

    private fun addTag(){

        addTag.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                Toast.makeText(context, "Tag added", Toast.LENGTH_LONG).show()
                val chip = Chip(context)
                chip.text = addTag?.text.toString()
                chip.setTextColor(Color.BLACK)
                chip.setChipBackgroundColorResource(R.color.chipBackColor)
                chip.chipCornerRadius = 50f
                chip.setChipIconResource(R.drawable.ic_baseline_clear_24)
                write_chipGroup.addView(chip)
                chip.requestFocus()
                tagList.add(chip.text.toString())
                Log.i("haha", tagList.toString())
                chip.setOnClickListener {
                    write_chipGroup.removeView(it)
                    tagList.remove(tagList.indexOf(chip.text).toString())
                }

                return@OnKeyListener true
            }
            false
        })
    }


}