package com.conamobile.tictactoefirebase.core.shared

import android.content.Context

class SharedPreferences(context: Context) {
    private val pref = context.getSharedPreferences("key", Context.MODE_PRIVATE)

    fun isSavedEmail(isSavedEmail: String) {
        val editor = pref.edit()
        editor.putString("isSavedEmail", isSavedEmail)
        editor.apply()
    }

    fun getSavedEmail(): String? {
        return pref.getString("isSavedEmail", null)
    }

    fun isSavedUid(isSavedUid: String) {
        val editor = pref.edit()
        editor.putString("isSavedUid", isSavedUid)
        editor.apply()
    }

    fun getSavedUid(): String? {
        return pref.getString("isSavedUid", null)
    }
}