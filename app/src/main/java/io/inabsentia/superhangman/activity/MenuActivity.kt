package io.inabsentia.superhangman.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
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
import io.inabsentia.superhangman.retrofit.RetrofitClient
import io.inabsentia.superhangman.retrofit.interfaces.BooleanCallback
import io.inabsentia.superhangman.retrofit.interfaces.IntegerCallback
import io.inabsentia.superhangman.retrofit.interfaces.UserCallback
import io.inabsentia.superhangman.singleton.App
import java.util.*


class MenuActivity : AppCompatActivity(), View.OnClickListener {

    private var tvCustomTitle: TextView? = null
    private var tvWelcome: TextView? = null
    private var btnPlay: Button? = null
    private var btnLobby: Button? = null
    private var btnHighScores: Button? = null
    private var btnSendEmail: Button? = null
    private var btnChangePassword: Button? = null
    private var btnUserInfo: Button? = null

    private var welcomeImage: ImageView? = null
    private var usersOnline: TextView? = null

    private val app = App.instance
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
        btnLobby = findViewById(R.id.btn_lobby)
        btnHighScores = findViewById(R.id.btn_high_scores)
        btnSendEmail = findViewById(R.id.btn_send_email)
        btnChangePassword = findViewById(R.id.btn_change_password)
        btnUserInfo = findViewById(R.id.btn_user_information)

        welcomeImage = findViewById(R.id.welcome_img)
        usersOnline = findViewById(R.id.users_online)

        /*
         * Set I/O listeners.
         */
        btnPlay!!.setOnClickListener(this)
        btnLobby!!.setOnClickListener(this)
        btnHighScores!!.setOnClickListener(this)
        btnSendEmail!!.setOnClickListener(this)
        btnChangePassword!!.setOnClickListener(this)
        btnUserInfo!!.setOnClickListener(this)
        welcomeImage!!.setOnClickListener(this)

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.welcome_title)

        retrofitClient!!.getLoggedInUser(app?.username, object : UserCallback {
            override fun onSuccess(user: Bruger?) {
                tvWelcome?.text = "Welcome ${user?.fornavn} ${user?.efternavn}!"
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to fetch logged in user!", Toast.LENGTH_LONG).show()
            }
        })

        retrofitClient!!.getCurrentUserAmount(object : IntegerCallback {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(value: Int) {
                usersOnline!!.text = "Users online: $value"
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to fetch users online!", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.welcome_img -> welcomeImage!!.rotation = random.nextFloat() * MAXIMUM_IMAGE_ROT
            R.id.btn_play -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, GameActivity::class.java))
            }
            R.id.btn_high_scores -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, HighScoreActivity::class.java))
            }
            R.id.btn_send_email -> {
                welcomeImage!!.rotation = 0f
                startActivity(Intent(this, GuideActivity::class.java))
            }
        }
    }

    companion object {
        private val random = Random()
        private val MAXIMUM_IMAGE_ROT = 5000
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    // do the acknowledged action, beware, this is run on UI thread
                    retrofitClient?.logOut(app?.username, object : BooleanCallback {
                        override fun onSuccess(value: Boolean) {
                            Toast.makeText(applicationContext, "Successfully logged out!", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure() {
                            Toast.makeText(applicationContext, "Failed to log out!", Toast.LENGTH_SHORT).show()
                        }
                    })
                    super.onBackPressed()
                }
                .create()
                .show()
    }

}