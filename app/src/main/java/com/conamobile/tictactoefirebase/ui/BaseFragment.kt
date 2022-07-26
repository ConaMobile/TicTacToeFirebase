package com.conamobile.tictactoefirebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.conamobile.tictactoefirebase.core.shared.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


open class BaseFragment(private val layoutRes: Int) : Fragment() {
    var mAuth: FirebaseAuth? = null
    var database = FirebaseDatabase.getInstance()
    var myRef = database.reference
    lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return if (layoutRes != 0)
            inflater.inflate(layoutRes, container, false)
        else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreferences(requireContext())
    }

    fun tst(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun navigator(fragment: Int) {
        findNavController().navigate(fragment)
    }

    inline fun View.click(crossinline clickListener: (View) -> Unit) {
        setOnClickListener { clickListener(this) }
    }

    fun nowDate(): String {
        val simpleDateFormat = SimpleDateFormat("EEE, d MMM | HH:mm")
        val date = Date()
        return simpleDateFormat.format(date)
    }

    fun splitString(str: String): String {
        return str.split("@")[0]
    }
}