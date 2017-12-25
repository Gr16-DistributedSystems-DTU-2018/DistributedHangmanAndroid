package io.inabsentia.superhangman.singleton

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.preference.PreferenceManager
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.activity.MenuActivity
import io.inabsentia.superhangman.data.dao.HighScoreDAO
import io.inabsentia.superhangman.data.dao.MatchDAO
import io.inabsentia.superhangman.data.dto.HighScoreDTO
import io.inabsentia.superhangman.data.dto.MatchDTO
import io.inabsentia.superhangman.logic.GameLogic
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*


@SuppressLint("Registered")
class App private constructor() : Application() {

    val MUSIC_ENABLED = true
    val WORD_URL = "https://www.nytimes.com"

    private var prefs: SharedPreferences? = null

    private val logic = GameLogic.instance
    private val matchDAO = MatchDAO.instance
    private val highScoreDAO = HighScoreDAO.instance

    fun recordMatch(context: Context) {
        val name = getDisplayName(context)
        val matchDTO = MatchDTO(name, logic!!.score, logic.latestGameStatus, roundDouble(logic.timeUsed, 2), logic.secretWord!!)

        matchDAO!!.add(matchDTO)
        matchDAO.save(context)
    }

    fun recordHighScore(context: Context) {
        if (!checkIfHighScore(context)) return

        val name = getDisplayName(context)
        val highScoreDTO = HighScoreDTO(name, logic!!.score)

        highScoreDAO!!.add(highScoreDTO)
        highScoreDAO.save(context)
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
        val highestScore = highScoreDAO!!.getCurrentHighScore(name)
        return logic!!.score > highestScore
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

    @SuppressLint("ObsoleteSdkInt")
    fun updateLangResources(context: Context, activity: MenuActivity, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }

        resources.updateConfiguration(configuration, resources.displayMetrics)
        activity.recreate()
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