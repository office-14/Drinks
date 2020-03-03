package com.office14.coffeedose.repository

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.office14.coffeedose.CoffeeDoseApplication

object PreferencesRepository {

    private const val APP_THEME_KEY = "APP_THEME_KEY"
    private const val ORDER_ID_KEY = "ORDER_ID_KEY"
    private const val BASE_URL_KEY = "BASE_URL_KEY"

    private const val BASE_URL = "http://10.0.2.2:5000/api/"

    private val prefs : SharedPreferences by lazy {
        val ctx = CoffeeDoseApplication.applicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun saveBaseUrl(url:String){
        putValue(BASE_URL_KEY to url)
    }

    fun getBaseUrl() = prefs.getString(BASE_URL_KEY,BASE_URL)

    fun getLastOrderId() = prefs.getInt(ORDER_ID_KEY,-1)

    fun saveLastOrderId(orderId:Int) = putValue(ORDER_ID_KEY to orderId)

    fun saveAppTheme(theme: Int) {
        putValue(APP_THEME_KEY to theme)
    }

    fun getAppTheme(): Int = prefs.getInt(APP_THEME_KEY, AppCompatDelegate.MODE_NIGHT_NO)


    private fun putValue(pair : Pair<String, Any>) = with(prefs.edit()){
        val key = pair.first
        val value = pair.second

        when (value){
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitives types can be stored")
        }

        apply()
    }
}