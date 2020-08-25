package com.a.amp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val summaryList = mutableListOf<RelatedCvDataItem>()
        val relatedList = mutableListOf<RelatedCvDataItem>()
        repeat(10) {
            summaryList.add(
                RelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )

            relatedList.add(
                RelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )
        }

        val myAdapter2 = SummaryCvAdapter(summaryList)
        val myAdapter = RelatedCvAdapter(relatedList)

        home_page_recycle_2.apply {
            adapter = myAdapter2
            setHasFixedSize(true)
        }

        home_page_recycle_1.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        home_appbar_end_icon.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
        home_appbar_start_icon.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWriteFragment())
        }
    }
}