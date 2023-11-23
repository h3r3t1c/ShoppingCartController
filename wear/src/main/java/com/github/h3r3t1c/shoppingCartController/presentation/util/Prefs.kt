package com.github.h3r3t1c.shoppingCartController.presentation.util

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val PREF_INITIAL_ACK = "initial_ack"
    private fun getPrefs(context:Context):SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    fun isInitialAckSet(context: Context):Boolean = getPrefs(context).getBoolean(PREF_INITIAL_ACK, false)
    fun setInitialAckSet(context:Context) = getPrefs(context).edit().putBoolean(PREF_INITIAL_ACK, true).commit()
}