package com.a.amp.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.a.amp.R
import com.a.amp.storage.setting
import com.a.amp.user.data.MoreClickListner
import kotlinx.android.synthetic.main.fragment_profile_tab2.*

class ProfileTabFragment2(private val username: String) : Fragment(), MoreClickListner {

    lateinit var profileViewModel: ProfileListViewModel
    var myAdapter: WritingCvAdapter? = null
    lateinit var currentUser: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return layoutInflater.inflate(R.layout.fragment_profile_tab2, container, false)
    }

    override fun onResume() {
        super.onResume()

        profileViewModel = ViewModelProvider(this).get(ProfileListViewModel::class.java)

        myAdapter = profileViewModel.writeList2.value?.let {
            WritingCvAdapter(
                it, this, currentUser, username
            )
        }

        profile_recycler_2.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        profileViewModel.writeList2.observe(viewLifecycleOwner, { list ->
            if (list != null) {
                myAdapter?.list = list
                myAdapter?.notifyDataSetChanged()
            }
        })

        profileViewModel.fillWrite2(username)

    }

    override fun onStart() {
        super.onStart()
        val setting = setting()
        currentUser = setting.getString("username")
    }

    override fun onClick(id: String, layoutPosition: Int) {

    }
}