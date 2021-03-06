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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.core.resource.Status
import com.a.amp.databinding.FragmentWriteBinding
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_write.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteFragment : Fragment() {

    private var tagList = mutableListOf<String>()
    private lateinit var Binding: FragmentWriteBinding
    lateinit var slug: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slug = arguments?.let { WriteFragmentArgs.fromBundle(it).slug }.toString()
    }

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

        val tagListViewModel = ViewModelProvider(this)
            .get(TagListViewModel::class.java)

        Binding.also {
            it.vm = writeViewModel
            it.lifecycleOwner = this
        }

        if (slug.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch{
                writeViewModel.getArticleAndTags(slug)
            }
            writeViewModel.resultArticle.observe(this as LifecycleOwner) {list ->
                if (list.size > 0) {
                    title.setText(list[0].title)
                    body.setText(list[0].text)
                }
            }

            writeViewModel.tagList.observe(viewLifecycleOwner, { list ->
                if (list != null) {
                    tagList = list
                    for (i in tagList.indices) {
                        val chip = Chip(context)
                        chip.text = tagList[i]
                        chip.setTextColor(Color.BLACK)
                        chip.setChipBackgroundColorResource(R.color.chipBackColor)
                        chip.chipCornerRadius = 50f
                        chip.isCloseIconVisible=true
                        write_chipGroup.addView(chip)
                        chip.setOnCloseIconClickListener {
                            write_chipGroup.removeView(it)
                            tagList.remove(tagList.indexOf(chip.text).toString())
                        }
                        chip.setOnClickListener {
                            val text = chip.text
                        }
                    }
                }
            })

            write_btn_1.text = "تصحیح مقاله"
            write_btn_1.setOnClickListener {
                if (isValid()) {
                    writeViewModel.edit(tagList, slug)
                }
            }
        } else if (slug.isEmpty()) {
            write_btn_1.setOnClickListener {
                if (isValid()) {
                    writeViewModel.create(tagList)
                }
            }
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

        writeViewModel.editResult.observe(viewLifecycleOwner, { result ->
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

        addTag.setOnClickListener {
            addTag()
        }
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
                chip.isCloseIconVisible = true
                write_chipGroup.addView(chip)
                chip.requestFocus()
                tagList.add(chip.text.toString())
                Log.i("haha", tagList.toString())
                chip.setOnCloseIconClickListener {
                    write_chipGroup.removeView(it)
                    tagList.remove(tagList.indexOf(chip.text).toString())
                }

                return@OnKeyListener true
            }
            false
        })
    }
}