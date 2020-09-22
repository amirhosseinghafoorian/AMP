package com.a.amp.article.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.core.resource.Status
import com.a.amp.databinding.FragmentWriteBinding
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_write.*

class WriteFragment : Fragment() {

    private val tagList = mutableListOf<String>()
    private lateinit var Binding: FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWriteBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_write, container, false
        )
        Binding = binding
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val writeViewModel = ViewModelProvider(this)
            .get(WriteViewModel::class.java)

        Binding.also {
            it.vm = writeViewModel
            it.lifecycleOwner = this
        }

        write_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        writeViewModel.result.observe(viewLifecycleOwner, { result ->
            if (result.status == Status.SUCCESS && result.code == 200) {
                Toast.makeText(context, "مقاله ایجاد شد", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            } else if (result.status == Status.SUCCESS && result.code != 200) {
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT)
                    .show()
            } else if (result.status == Status.ERROR) {
                Toast.makeText(context, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        write_btn_1.setOnClickListener {
            if (isValid()) {
                writeViewModel.create(tagList)
            }
        }

        addTag.setOnClickListener {
            addTag()
        }

//        write_chipGroup.setOnClickListener {
//
//        }

    }

    private fun isValid(): Boolean {
        write_et_1.error = null
        write_et_2.error = null
        write_et_1.isErrorEnabled = false
        write_et_2.isErrorEnabled = false
        if (write_et_1.editText?.text.toString() == "") {
            write_et_1.error = "عنوان را وارد کنید"
            write_et_1.requestFocus()
            return false
        } else if (write_et_2.editText?.text.toString() == "") {
            write_et_2.error = "متن مقاله را وارد کنید"
            write_et_2.requestFocus()
            return false
        }
        return true
    }

    private fun addTag() {
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