package com.a.amp.article.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.a.amp.article.data.ArticleRemote
import com.a.amp.core.resource.Status
import com.a.amp.databinding.FragmentArticleBinding
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.comment_dialog.*
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private var slug = ""
    var isFavrite = false
    lateinit var tagList: MutableList<String>
//    private val ARG_PARAM1 = "ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val args : ArticleFragment by navArgs()
//        slug = args.slug
        slug = arguments?.let { ArticleFragmentArgs.fromBundle(it).slug }.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val articleViewModel = ViewModelProvider(this).get(ArticleListViewModel::class.java)

        val myAdapter = articleViewModel.relatedList.value?.let { ArticleRelatedCvAdapter(it) }
        val myAdapter2 = articleViewModel.commentList.value?.let { CommentCvAdapter(it) }

        articleViewModel.getFavoriteFromServer(slug)
        suspend { articleViewModel.fillSingleArticleWithComments(slug) }


        article_page_recycle_1.apply {
            adapter = myAdapter
//            setHasFixedSize(true)
        }

        article_page_recycle_2.apply {
            adapter = myAdapter2
//            setHasFixedSize(true)
        }

        articleViewModel.relatedList.observe(viewLifecycleOwner, { list ->
            if (list != null) {
                myAdapter?.list = list
                myAdapter?.notifyDataSetChanged()
            }
        })

        articleViewModel.commentList.observe(viewLifecycleOwner, { list ->
            if (list != null) {
                myAdapter2?.list = list
                myAdapter2?.notifyDataSetChanged()
            }
        })

        articleViewModel.tagList.observe(viewLifecycleOwner, { list ->
            if (list != null) {
                tagList = list
                article_chipGroup.removeAllViews()
                for (i in tagList.indices) {
                    val chip = Chip(context)
                    chip.text = tagList[i]
                    chip.setTextColor(Color.BLACK)
                    chip.setChipBackgroundColorResource(R.color.chipBackColor)
                    chip.chipCornerRadius = 50f
                    article_chipGroup.addView(chip)
                    chip.setOnClickListener {
                        val text = chip.text.toString()
                        findNavController().navigate(
                            ArticleFragmentDirections.actionArticleFragmentToTagFragment(
                                text
                            )
                        )
                    }
                }
            }
        })

        articleViewModel.singleArticle.observe(viewLifecycleOwner, { list ->
            if (list.size > 0) {
                binding.articleBind = list[0]
            }
        })

        CoroutineScope(Dispatchers.IO).launch {
            articleViewModel.fillRelated()
            articleViewModel.fillSingleArticleWithComments(slug) // and also tags
        }

        article_iv_1.setOnClickListener {
            findNavController().navigate(
                ArticleFragmentDirections.actionArticleFragmentToProfileFragment(
                    articleViewModel.singleArticle.value?.get(0)?.name.toString(), ""
                )
            )
        }

//        article_chip_1.setOnClickListener {

//        }

        article_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        articleViewModel.favorited.observe(viewLifecycleOwner, { favorited ->
            if (favorited != null) {
                binding.saved = favorited
                isFavrite = favorited
            }
        })

        article_favorite_ic.setOnClickListener {
            if (isFavrite) {
                articleViewModel.unFavoriteArticle(slug)
            } else if (!isFavrite) {
                articleViewModel.favoriteArticle(slug)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = Dialog(requireContext())
        val view = layoutInflater.inflate(R.layout.comment_dialog, null)

        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(1000,1000)

        article_btn_1.setOnClickListener {
            dialog.show()
            dialog.comment_btn.setOnClickListener {
                val body = dialog.comment_et.editText?.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val ar = ArticleRemote()
                    val result = ar.addCommentToServer(slug, body)
                    if (result.status == Status.SUCCESS && result.code == 200) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "کامنت اضافه شد", Toast.LENGTH_SHORT)
                                .show()
                            dialog.dismiss()
                        }
                    } else if (result.status == Status.SUCCESS && result.code != 200) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "خطا", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else if (result.status == Status.ERROR) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

//        Log.i("bang", tagList.toString())


//        if (tagList.isNotEmpty()) {
//            for (i in tagList.indices) {
//                val chip = Chip(context)
//                chip.text = tagList[i]
//                chip.setTextColor(Color.BLACK)
//                chip.setChipBackgroundColorResource(R.color.chipBackColor)
//                chip.chipCornerRadius = 50f
//                chip.setCloseIconResource(R.drawable.ic_baseline_clear_24)
//                article_chipGroup.addView(chip)
//            }
//
//        }

    }
}