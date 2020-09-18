package com.a.amp.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.a.amp.R
import com.a.amp.databinding.FragmentProfileBinding
import com.a.amp.user.data.MoreClickListner
import com.a.amp.user.data.ProfileDataItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), MoreClickListner {

    private lateinit var binding: FragmentProfileBinding
    private var username = ""
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = arguments?.let { ProfileFragmentArgs.fromBundle(it).username }.toString()
        id = arguments?.let { ProfileFragmentArgs.fromBundle(it).id }.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val profileViewModel = ViewModelProvider(this).get(ProfileListViewModel::class.java)

        binding.profileBind = ProfileDataItem(username, id)

        val myAdapter = profileViewModel.writeList.value?.let {
            WritingCvAdapter(
                it, this
                //        ,
                //            {
                //              call back body
                //            }
            )
        }

        profile_recycler.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        profileViewModel.writeList.observe(viewLifecycleOwner, {
            myAdapter?.notifyDataSetChanged()
        })

        profileViewModel.fillWrite(username)

        profile_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

    override fun onClick(id: String, layoutPosition: Int) {
        val bottonSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)

        bottonSheetDialog.setContentView(view)

        bottonSheetDialog.show()
    }
}