package com.conamobile.tictactoefirebase.ui.splash

import android.os.Handler
import android.os.Looper
import com.conamobile.tictactoefirebase.R
import com.conamobile.tictactoefirebase.ui.BaseFragment

class SplashFragment : BaseFragment(0) {
    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            startMain()
        }, 300)
    }

    private fun startMain() {
        if (sharedPref.getSavedEmail() != null && sharedPref.getSavedUid() != null) {
            navigator(R.id.action_splashFragment_to_homeFragment)
        } else {
            navigator(R.id.action_splashFragment_to_loginFragment)
        }
    }
}