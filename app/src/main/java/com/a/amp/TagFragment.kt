package com.a.amp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.a.amp.databinding.FragmentTagBinding
import kotlinx.android.synthetic.main.fragment_tag.*

class TagFragment : Fragment() {
    private lateinit var binding: FragmentTagBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tag, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tagViewModel = ViewModelProvider(this).get(TagListViewModel::class.java)

        val myAdapter = tagViewModel.summaryList.value?.let { SummaryCvAdapter(it) }

        tag_recycler.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        tagViewModel.summaryList.observe(viewLifecycleOwner, { list ->
            myAdapter?.notifyDataSetChanged()
        })

        tagViewModel.fillSummary()
    }

}