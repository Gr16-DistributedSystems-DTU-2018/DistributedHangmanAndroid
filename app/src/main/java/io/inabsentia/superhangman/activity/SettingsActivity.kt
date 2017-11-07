package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceActivity
import android.support.design.widget.Snackbar
import android.view.View
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
        val root: View = listView
        return when (pref) {
            btnResetMatchHistoryPref -> {
                matchDAO!!.removeAll(baseContext)
                val snackbar: Snackbar = Snackbar.make(root, "Match History has been reset", Snackbar.LENGTH_SHORT)
                snackbar.setActionTextColor(resources.getColor(R.color.textColor))
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorAccent))
                snackbar.show()
                true
            }
            btnResetHighScoresPref -> {
                highScoreDAO!!.removeAll(baseContext)
                val snackbar: Snackbar = Snackbar.make(root, "High Scores has been reset", Snackbar.LENGTH_SHORT)
                snackbar.setActionTextColor(resources.getColor(R.color.textColor))
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorAccent))
                snackbar.show()
                true
            }
            else -> true
        }
    }

}