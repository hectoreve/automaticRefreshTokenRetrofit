package com.example.tokenizedretrofit.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object PreferencesProvider {
    fun set(context: Context, key: String, value: String) {
        val editor = prefs(context).edit()
        editor.putString(key, value).apply()
    }

    fun string(context: Context, key: String): String? {
        return prefs(context).getString(key, "")
    }

    fun set(context: Context, key: String, value: Boolean) {
        val editor = prefs(context).edit()
        editor.putBoolean(key, value).apply()
    }

    fun bool(context: Context, key: String): Boolean? {
        return prefs(context).getBoolean(key, false)
    }

    fun remove(context: Context, key: String) {
        val editor = prefs(context).edit()
        editor.remove(key).apply()
    }

    // Elimina las SharedPreferences del dominio app
    fun clear(context: Context) {
        val editor = prefs(context).edit()
        editor.clear().apply()
    }

    // Private

    private fun prefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

}