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
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private var slug = ""
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

        articleViewModel.singleArticle.observe(viewLifecycleOwner, { list ->
            if (list.size > 0) {
                binding.articleBind = list[0]
            }
        })

        CoroutineScope(Dispatchers.IO).launch {
            articleViewModel.fillRelated()
            articleViewModel.fillComment()
            articleViewModel.fillSingleArticle(slug)
        }

        article_iv_1.setOnClickListener {
            findNavController().navigate(
                ArticleFragmentDirections.actionArticleFragmentToProfileFragment(
                    articleViewModel.singleArticle.value?.get(0)?.name.toString(), ""
                )
            )
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
//                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}