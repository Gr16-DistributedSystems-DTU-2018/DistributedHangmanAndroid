package io.inabsentia.superhangman.activity

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.retrofit.RetrofitClient
import io.inabsentia.superhangman.retrofit.interfaces.LoginCallback
import io.inabsentia.superhangman.singleton.App

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val app = App.instance
    private var tvCustomTitle: TextView? = null

    private var btnLogin: Button? = null
    private var btnForgotPass: Button? = null
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null

    private var retrofitClient: RetrofitClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
        retrofitClient = RetrofitClient(this)

        /*
         * Check to see if the intro should be ran or not.
         */
        introCheck()

        tvCustomTitle = findViewById(R.id.action_bar_title)
        btnLogin = findViewById(R.id.btn_login)
        btnForgotPass = findViewById(R.id.btn_forgot_pass)
        etUsername = findViewById(R.id.username_field)
        etPassword = findViewById(R.id.password_field)

        btnLogin!!.setOnClickListener(this)

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.welcome_title)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_login -> {
                val username: String = etUsername?.text.toString()
                val password: String = etPassword?.text.toString()

                retrofitClient?.logIn(username, password, object : LoginCallback {
                    override fun onFailure() {
                        Toast.makeText(applicationContext, "Invalid Credentials!", Toast.LENGTH_LONG).show()
                    }

                    override fun onSuccess(value: Boolean) {
                        Toast.makeText(applicationContext, "Successfully signed in!", Toast.LENGTH_LONG).show()
                        val intentGame = Intent(applicationContext, MenuActivity::class.java)
                        startActivity(intentGame)
                    }

                })
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
                val intentIntro = Intent(this, IntroActivity::class.java)
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

}