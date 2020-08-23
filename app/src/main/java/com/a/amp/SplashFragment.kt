package com.a.amp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlin.math.log


class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()
        val handler = Handler()
        handler.postDelayed({
            if (true){          // will be checked with login status
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthenticate())
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }
        }, 3000)
    }

}