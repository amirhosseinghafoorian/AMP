package com.a.amp.home.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.a.amp.R
import com.a.amp.core.resource.Resource
import com.a.amp.core.resource.Status
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_home_tab2.*
import kotlinx.android.synthetic.main.fragment_write.*
import kotlinx.android.synthetic.main.fragment_write.write_chipGroup
import kotlinx.coroutines.*

class HomeTabFragment2 : Fragment() {

    lateinit var tagsList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_tab2, container, false)
    }

    lateinit var homeViewModel: HomeListViewModel
    override fun onResume() {
        super.onResume()
        homeViewModel = ViewModelProvider(this).get(HomeListViewModel::class.java)
        CoroutineScope((Dispatchers.IO)).launch {
            homeViewModel.getTags()

            swipe.setOnRefreshListener {
                reqTags()
            }

            withContext(Dispatchers.Main) {

                homeViewModel.tagList.observe(viewLifecycleOwner, { list ->
                    if (list != null) {
                        tagsList = list
                        for (i in tagsList.indices) {
                            val chip = Chip(context)
                            chip.text = tagsList[i]
                            chip.setTextColor(Color.BLACK)
                            //chip.setChipBackgroundColorResource(R.color.chipBackColor)
                            chip.chipCornerRadius = 50f
                            chip.setBackgroundColor(Color.GRAY)
                            home_chipGroup.addView(chip)
                            chip.setOnClickListener {
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToTagFragment(
                                        chip.text.toString()
                                    )
                                )
                            }
                        }
                    }
                })

                homeViewModel.tagListResult.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        swipe.isRefreshing = it.status == Status.LOADING
                    }
                })
            }
        }
    }

    private fun reqTags() {
        GlobalScope.launch(Dispatchers.IO) {
            homeViewModel.getTags()
        }
    }

}