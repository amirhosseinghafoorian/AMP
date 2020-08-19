package com.a.amp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.signup.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class login_fr : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        Toast.makeText(context, "$param1 and $param2", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onStart() {
        super.onStart()
        login_box_1.editText?.setText(param1)
        login_box_2.editText?.setText(param2)
        login_box_3.setOnClickListener{
            val test_fr = home_fr.newInstance(login_box_1.editText?.text.toString(),login_box_2.editText?.text.toString())
            test_fr.arguments
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame, test_fr)
            fragmentTransaction?.addToBackStack("fr3")
            fragmentTransaction?.commit()
        }
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            login_fr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}