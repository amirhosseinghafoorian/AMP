package com.a.amp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.a.amp.databinding.FragmentArticleBinding
import kotlinx.android.synthetic.main.fragment_article.*


private const val ARG_PARAM1 = "ID"
class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private var param1: Int = 0

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

        binding.articleBind = WritingCvDataItem(
            "تیتر اولیه مقاله",
            "متنی برای تست چند متن اصلی مقاله",
            "امیرحسین غفوریان",
            "5 روز پیش",
            0
        )

        val relatedList = mutableListOf<RelatedCvDataItem>()
        val commentList = mutableListOf<CommentCvDataItem>()

        repeat(10) {
            relatedList.add(
                RelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )

            commentList.add(
                CommentCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", 0
                )
            )
        }

        val myAdapter = RelatedCvAdapter(relatedList)
        val myAdapter2 = CommentCvAdapter(commentList)

        article_page_recycle_1.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        article_page_recycle_2.apply {
            adapter = myAdapter2
            setHasFixedSize(true)
        }

        article_iv_1.setOnClickListener {
            findNavController().navigate(ArticleFragmentDirections.actionArticleFragmentToProfileFragment())
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