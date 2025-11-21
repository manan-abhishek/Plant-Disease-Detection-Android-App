package com.example.androidproject

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        private const val PREF_NAME = "UserSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_TOKEN = "token"
        private const val KEY_EMAIL = "email"
        private const val KEY_USER_ID = "userId"
    }

    // Save login session
    fun saveLoginSession(token: String?, email: String?, userId: String? = null) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_EMAIL, email)
        userId?.let { editor.putString(KEY_USER_ID, it) }
        editor.apply()
    }

    // Check if user is logged in
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Get saved token
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    // Get saved email
    fun getEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    // Get user ID
    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    // Clear session (logout)
    fun clearSession() {
        editor.clear()
        editor.apply()
    }

    // Logout user
    fun logout() {
        clearSession()
    }
}

