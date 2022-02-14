package com.example.tokenizedretrofit.providers

import android.content.Context
import android.widget.Toast

class SessionManager(private val context: Context) {

    companion object {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        Toast.makeText(context, "Logged in saving token $token", Toast.LENGTH_LONG).show()
        PreferencesProvider.set(context, USER_TOKEN, token)
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return PreferencesProvider.string(context, USER_TOKEN)
    }
}