package com.conamobile.tictactoefirebase.ui.login

import android.os.Bundle
import android.view.View
import com.conamobile.tictactoefirebase.R
import com.conamobile.tictactoefirebase.core.utils.viewBinding
import com.conamobile.tictactoefirebase.databinding.FragmentLoginBinding
import com.conamobile.tictactoefirebase.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding { FragmentLoginBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        nextBtnManager()
        setupMotion()
    }

    private fun nextBtnManager() {
        binding.apply {
            binding.nextBtn.click {
                if (email.text!!.isNotEmpty() && password.text!!.isNotEmpty()) {
                    loginToFireBase(email.text.toString(), password.text.toString())
                } else {
                    tst(getString(R.string.email_or_password))
                }
            }
        }
    }

    private fun setupMotion() {
        binding.motionLayout.transitionToEnd()
    }

    private fun loginToFireBase(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->

                if (task.isSuccessful) {
                    tst("Successful login")
                    val currentUser = mAuth!!.currentUser
                    if (currentUser != null) {
                        myRef.child("Users").child(splitString(currentUser.email.toString()))
                            .child("Request").setValue(currentUser.uid)
                    }
                    startMain()
                } else {
                    tst("Login Failed")
                }
            }
            .addOnFailureListener {
                tst("Login Failed: ${it.message}")
            }

    }

    private fun startMain() {
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            sharedPref.isSavedEmail(currentUser.email!!)
            sharedPref.isSavedUid(currentUser.uid)
            navigator(R.id.action_loginFragment_to_homeFragment)
        }
    }
}