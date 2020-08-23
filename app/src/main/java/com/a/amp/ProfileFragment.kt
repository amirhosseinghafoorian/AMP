package com.a.amp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_article.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialDialogs
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_write.*
import kotlinx.android.synthetic.main.writing_cv.*

class ProfileFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottonSheetDialog = BottomSheetDialog(requireContext())
        val  view = layoutInflater.inflate(R.layout.bottom_sheet,null)

        bottonSheetDialog.setContentView(view)

        profile_cv_btn_1.setOnClickListener {
            bottonSheetDialog.show()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val writingList = mutableListOf<WritingCvDataItem>()
        repeat(10) {
            writingList.add(WritingCvDataItem(" سه خط مقاله : $it"," دو خط مقاله : $it",
                    " نام کاربر : $it" , "$it روز پیش ",0))
        }

        val myAdapter = WritingCvAdapter(writingList)

        profile_recycler.apply {
            adapter = myAdapter
            setHasFixedSize(true)
        }

        profile_appbar_start_icon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }
}