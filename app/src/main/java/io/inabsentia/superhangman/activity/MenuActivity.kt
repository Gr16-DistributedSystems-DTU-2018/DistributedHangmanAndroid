package io.inabsentia.superhangman.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import brugerautorisation.data.Bruger
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dao.HighScoreDAO
import io.inabsentia.superhangman.data.dao.MatchDAO
import io.inabsentia.superhangman.retrofit.RetrofitClient
import io.inabsentia.superhangman.retrofit.interfaces.GetCurrentUserCallback
import io.inabsentia.superhangman.singleton.App
import java.util.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    private var tvCustomTitle: TextView? = null
    private var tvWelcome: TextView? = null
    private var btnPlay: Button? = null
    private var btnMatchHistory: Button? = null
    private var btnHighScores: Button? = null
    private var btnGuide: Button? = null
    private var btnLang: Button? = null
    private var btnAbout: Button? = null
    private var welcomeImage: ImageView? = null

    private val app = App.instance
    private val matchDAO = MatchDAO.instance
    private val highScoreDAO = HighScoreDAO.instance
    private var retrofitClient: RetrofitClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
        retrofitClient = RetrofitClient(this)

        /*
         * Instantiate objects.
         */
        tvWelcome = findViewById(R.id.tv_user_welcome)
        tvCustomTitle = findViewById(R.id.action_bar_title)
        btnPlay = findViewById(R.id.btn_play)
        btnMatchHistory = findViewById(R.id.btn_match_history)
        btnHighScores = findViewById(R.id.btn_high_scores)
        btnGuide = findViewById(R.id.btn_guide)
        btnLang = findViewById(R.id.btn_lang)
        btnAbout = findViewById(R.id.btn_about)
        welcomeImage = findViewById(R.id.welcome_img)


        retrofitClient!!.getCurrentUser(object : GetCurrentUserCallback {
            override fun onSuccess(user: Bruger?) {
                tvWelcome?.text = "Welcome " + user?.fornavn + " " + user?.efternavn + "!"
            }

            override fun onFailure() {

            }
        })

        // Lang invisible and disabled for now.
        btnLang!!.visibility = View.INVISIBLE
        btnLang!!.isEnabled = false

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
        btnLang!!.setOnClickListener(this)
        btnAbout!!.setOnClickListener(this)
        welcomeImage!!.setOnClickListener(this)

        /*
         * Start playing sound track if MUSIC_ENABLED
         * is equal to true.
         */
        if (!mediaPlayer!!.isPlaying && !isPlaying && app!!.MUSIC_ENABLED) {
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
                startActivity(Intent(this, GameActivity::class.java))
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
            R.id.btn_lang -> {
                Toast.makeText(this, "This feature is not yet implemented!", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_about -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
    }

    companion object {
        private val random = Random()
        private var mediaPlayer: MediaPlayer? = null

        private val MAXIMUM_IMAGE_ROT = 5000
        private var isPlaying = false
    }

}