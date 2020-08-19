package com.a.amp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.home_page.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.signup.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class home_fr : Fragment() {
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
        return inflater.inflate(R.layout.home_page, container, false)
    }

    override fun onStart() {
        super.onStart()
//        home_tv_1.setText(param1)
//        home_tv_2.setText(param2)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            home_fr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}