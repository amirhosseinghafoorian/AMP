package com.a.amp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.a.amp.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), MoreClickListner {
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val writingList = mutableListOf<WritingCvDataItem>()

        binding.profileBind = ProfileDataItem("امیرحسین غفوریان", "برنامه نویس اندروید", 0)

        repeat(10) {
            writingList.add(
                WritingCvDataItem(
                    " سه خط مقاله : $it", " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )
        }

        val myAdapter = WritingCvAdapter(
            writingList, this
//        ,
//            {
//              call back body
//            }
        )

        profile_recycler.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        profile_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

    override fun onClick(id: Int, layoutPosition: Int) {
        val bottonSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)

        bottonSheetDialog.setContentView(view)

        bottonSheetDialog.show()
    }
}