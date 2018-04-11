package io.inabsentia.superhangman.singleton

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.logic.GameLogic
import java.math.BigDecimal
import java.math.RoundingMode

@SuppressLint("Registered")
class App private constructor() : Application() {

    val MUSIC_ENABLED = false
    val WORD_URL = "https://www.nytimes.com"
    val BASE_URL = "http://ubuntu4.javabog.dk:8080/web/rest/"

    var username: String? = null
    var password: String? = null

    private var prefs: SharedPreferences? = null

    private val logic = GameLogic.instance

    fun recordMatch(context: Context) {
        val name = getDisplayName(context)
    }

    fun recordHighScore(context: Context) {
        if (!checkIfHighScore(context)) return

        val name = getDisplayName(context)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        } catch (e: Exception) {
            false
        }
    }

    private fun checkIfHighScore(context: Context): Boolean {
        val name = getDisplayName(context)
        return true
    }

    private fun roundDouble(value: Double, places: Int): Double {
        if (places < 0) throw IllegalArgumentException()
        var bd = BigDecimal(value)
        bd = bd.setScale(places, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

    fun getDisplayName(context: Context): String {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var name = prefs!!.getString(context.resources.getString(R.string.pref_user_name), null)

        if (name == null)
            name = context.getString(R.string.pref_default_display_name)

        return name
    }

    companion object {
        @get:Synchronized
        var instance: App? = null
            private set

        init {
            instance = App()
        }
    }

}