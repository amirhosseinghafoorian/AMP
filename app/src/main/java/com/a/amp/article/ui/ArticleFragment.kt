package com.a.amp.article.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.databinding.FragmentArticleBinding
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private var param1: Int = 0
    private val ARG_PARAM1 = "ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
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

        binding.articleBind = WritingCvDataItem(
            "تیتر اولیه مقاله",
            "متنی برای تست چند متن اصلی مقاله",
            "امیرحسین غفوریان",
            "5 روز پیش",
            0
        )

        val myAdapter = articleViewModel.relatedList.value?.let { RelatedCvAdapter(it) }
        val myAdapter2 = articleViewModel.commentList.value?.let { CommentCvAdapter(it) }

        article_page_recycle_1.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        article_page_recycle_2.apply {
            adapter = myAdapter2
            setHasFixedSize(true)
        }

        articleViewModel.relatedList.observe(viewLifecycleOwner, { list ->
            myAdapter?.notifyDataSetChanged()
        })

        articleViewModel.commentList.observe(viewLifecycleOwner, { list ->
            myAdapter2?.notifyDataSetChanged()
        })

        articleViewModel.fillRelated()
        articleViewModel.fillComment()

        article_iv_1.setOnClickListener {
            findNavController().navigate(ArticleFragmentDirections.actionArticleFragmentToProfileFragment())
        }

        article_chip_1.setOnClickListener {
            findNavController().navigate(ArticleFragmentDirections.actionArticleFragmentToTagFragment())
        }

        article_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
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
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}