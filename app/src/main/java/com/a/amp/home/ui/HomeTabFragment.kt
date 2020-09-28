package com.a.amp.home.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.a.amp.R
import kotlinx.android.synthetic.main.fragment_home_tab.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_tab, container, false)
    }

    override fun onResume() {
        super.onResume()
        val homeViewModel = ViewModelProvider(this).get(HomeListViewModel::class.java)

        CoroutineScope((Dispatchers.IO)).launch {
            homeViewModel.fillSummary("")
            homeViewModel.fillRelated()

            withContext(Dispatchers.Main) {
                val myAdapter2 = homeViewModel.summaryList.value?.let { HomeSummaryCvAdapter(it) }
                val myAdapter = homeViewModel.relatedList.value?.let { HomeRelatedCvAdapter(it) }

                home_page_recycle_2.apply {
                    adapter = myAdapter2
                }

                home_page_recycle_1.apply {
                    adapter = myAdapter
                }

                homeViewModel.summaryList.observe(viewLifecycleOwner, { list ->
                    if (list != null) {
                        myAdapter2?.list = list
                        myAdapter2?.notifyDataSetChanged()
                    }
                })

                homeViewModel.relatedList.observe(viewLifecycleOwner, { list ->
                    if (list != null) {
                        myAdapter?.list = list
                        myAdapter?.notifyDataSetChanged()
                    }
                })

                home_page_et_1.editText?.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            homeViewModel.fillSummary(s.toString())
                        }
                    }
                })


            }
        }
    }

}