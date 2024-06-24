package com.example.mobileapp.ui.components

import android.content.Context
import android.content.SharedPreferences

object LanguagePreferences {
    private const val PREFS_NAME = "language_prefs"
    private const val KEY_LANGUAGE = "language"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setLanguage(context: Context, language: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_LANGUAGE, language)
        editor.apply()
    }

    fun getLanguage(context: Context): String {
        return getPreferences(context).getString(KEY_LANGUAGE, "en") ?: "en"
    }
}
