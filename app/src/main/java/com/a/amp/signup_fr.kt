package com.a.amp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import kotlinx.android.synthetic.main.signup.*
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class signup_fr : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup, container, false)
    }

    override fun onStart() {
        super.onStart()
        signup_box_5.setOnClickListener{
            val test_fr = login_fr.newInstance(signup_box_1.editText?.text.toString(),signup_box_3.editText?.text.toString())
            test_fr.arguments
            val fragmentTransaction = fragmentManager?.beginTransaction()
            // replace the FrameLayout with new Fragment
//            fragmentTransaction?.replace(R.id.frame, test_fr)
//            fragmentTransaction?.addToBackStack("fr2")
//            fragmentTransaction?.commit() // save the changes
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            signup_fr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}