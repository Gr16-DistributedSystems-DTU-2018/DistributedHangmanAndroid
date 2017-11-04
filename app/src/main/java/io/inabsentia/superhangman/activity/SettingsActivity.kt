package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceActivity
import android.widget.Toast
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dao.HighScoreDAO
import io.inabsentia.superhangman.data.dao.MatchDAO

class SettingsActivity : PreferenceActivity(), Preference.OnPreferenceClickListener {

    private var userNamePref: Preference? = null
    private var btnResetMatchHistoryPref: Preference? = null
    private var btnResetHighScoresPref: Preference? = null

    private val matchDAO = MatchDAO.instance
    private val highScoreDAO = HighScoreDAO.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)

        userNamePref = findPreference(getString(R.string.pref_user_name))
        btnResetMatchHistoryPref = findPreference(getString(R.string.pref_reset_match_history))
        btnResetHighScoresPref = findPreference(getString(R.string.pref_reset_high_score))

        userNamePref!!.onPreferenceClickListener = this
        btnResetMatchHistoryPref!!.onPreferenceClickListener = this
        btnResetHighScoresPref!!.onPreferenceClickListener = this
    }

    override fun onPreferenceClick(pref: Preference?): Boolean {
        return when (pref) {
            btnResetMatchHistoryPref -> {
                matchDAO!!.removeAll(baseContext)
                Toast.makeText(baseContext, "Match History has been reset.", Toast.LENGTH_SHORT).show()
                true
            }
            btnResetHighScoresPref -> {
                highScoreDAO!!.removeAll(baseContext)
                Toast.makeText(baseContext, "High Scores has been reset.", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }

}