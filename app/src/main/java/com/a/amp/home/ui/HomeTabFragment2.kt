package com.a.amp.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.a.amp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeTabFragment2 : Fragment() {

    lateinit var tagsList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_tab2, container, false)
    }

    override fun onResume() {
        super.onResume()
        val homeViewModel = ViewModelProvider(this).get(HomeListViewModel::class.java)

        CoroutineScope((Dispatchers.IO)).launch {
            homeViewModel.getTags()

            withContext(Dispatchers.Main) {

                homeViewModel.tagList.observe(viewLifecycleOwner, { list ->
                    if (list != null) {

                    }
                })

            }
        }
    }

}