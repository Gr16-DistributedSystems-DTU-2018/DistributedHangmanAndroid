package io.inabsentia.superhangman.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.asynctask.AsyncDownloadWords
import io.inabsentia.superhangman.data.dao.HighScoreDAO
import io.inabsentia.superhangman.data.dao.MatchDAO
import io.inabsentia.superhangman.singleton.App
import java.util.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    private var tvCustomTitle: TextView? = null
    private var btnPlay: Button? = null
    private var btnMatchHistory: Button? = null
    private var btnHighScores: Button? = null
    private var btnGuide: Button? = null
    private var welcomeImage: ImageView? = null

    private val app = App.instance
    private val matchDAO = MatchDAO.instance
    private val highScoreDAO = HighScoreDAO.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /* Execute AsyncTask for download of words if there's internet */
        if (app!!.isNetworkAvailable(baseContext))
            AsyncDownloadWords().execute()

        /*
         * Check to see if the intro should be ran or not.
         */
        introCheck()

        /*
         * Instantiate objects.
         */
        tvCustomTitle = findViewById(R.id.action_bar_title)
        btnPlay = findViewById(R.id.btn_play)
        btnMatchHistory = findViewById(R.id.btn_match_history)
        btnHighScores = findViewById(R.id.btn_high_scores)
        btnGuide = findViewById(R.id.btn_guide)
        welcomeImage = findViewById(R.id.welcome_img)

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.welcome_title)

        /* Instantiate mediaplayer */
        mediaPlayer = MediaPlayer.create(this, R.raw.game_sound)

        /*
         * Set I/O listeners.
         */
        btnPlay!!.setOnClickListener(this)
        btnMatchHistory!!.setOnClickListener(this)
        btnHighScores!!.setOnClickListener(this)
        btnGuide!!.setOnClickListener(this)
        welcomeImage!!.setOnClickListener(this)

        /*
         * Start playing sound track if MUSIC_ENABLED
         * is equal to true.
         */
        if (!mediaPlayer!!.isPlaying && !isPlaying && app.MUSIC_ENABLED) {
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
            isPlaying = true
        }

        /*
         * Load match histories from internal storage.
         */
        matchDAO!!.load(applicationContext)
        matchDAO.save(applicationContext)

        /*
         * Load high scores from internal storage.
         */
        highScoreDAO!!.load(applicationContext)
        highScoreDAO.save(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(Intent(this@MenuActivity, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.welcome_img -> welcomeImage!!.rotation = random.nextFloat() * MAXIMUM_IMAGE_ROT
            R.id.btn_play -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, PreGameActivity::class.java))
            }
            R.id.btn_match_history -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, MatchHistoryActivity::class.java))
            }
            R.id.btn_high_scores -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, HighScoreActivity::class.java))
            }
            R.id.btn_guide -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, GuideActivity::class.java))
            }
        }
    }

    private fun introCheck() {
        val key = "firstStart" + R.string.app_name

        /* Uncomment this line to getFromId the intro back */
        //PreferenceManager.getDefaultSharedPreferences(baseContext).edit().remove(key).apply()

        /* Declare a new thread to do a preference check */
        val thread = Thread {

            /* Initialize SharedPreferences */
            val getPrefs = PreferenceManager.getDefaultSharedPreferences(baseContext)

            /* Create a new boolean and preference and set it to true */
            val isFirstStart = getPrefs.getBoolean(key, true)

            /* If the activity has never started before... */
            if (isFirstStart) {
                /* Launch app intro */
                val intentIntro = Intent(this@MenuActivity, IntroActivity::class.java)
                runOnUiThread { startActivity(intentIntro) }

                /* Make a new preferences editor */
                val e = getPrefs.edit()

                /* Edit preference to make it false because we don't want this to run again */
                e.putBoolean(key, false)

                /* Apply the changes */
                e.apply()
            }
        }

        /* Start the thread */
        thread.start()
    }

    override fun onBackPressed() {

    }

    companion object {
        private val random = Random()
        private var mediaPlayer: MediaPlayer? = null

        private val MAXIMUM_IMAGE_ROT = 5000
        private var isPlaying = false
    }

}