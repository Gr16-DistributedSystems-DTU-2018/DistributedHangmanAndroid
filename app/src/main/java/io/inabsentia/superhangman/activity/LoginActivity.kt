package io.inabsentia.superhangman.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.retrofit.RetrofitClient
import io.inabsentia.superhangman.retrofit.interfaces.BooleanCallback
import io.inabsentia.superhangman.retrofit.interfaces.IntegerCallback
import io.inabsentia.superhangman.retrofit.interfaces.StringCallback
import io.inabsentia.superhangman.singleton.App


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val app: App? = App.getInstance()
    private var tvCustomTitle: TextView? = null

    private var btnLogin: Button? = null
    private var btnForgotPass: Button? = null
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var usersOnline: TextView? = null

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
        usersOnline = findViewById(R.id.users_online)

        retrofitClient!!.getCurrentUserAmount(object : IntegerCallback {
            override fun onSuccess(value: Int) {
                usersOnline!!.text = "Users online: $value"
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to fetch users online!", Toast.LENGTH_LONG).show()
            }
        })

        btnLogin!!.setOnClickListener(this)
        btnForgotPass!!.setOnClickListener(this)

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.welcome_title)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_login -> {
                val username: String = etUsername?.text.toString()
                val password: String = etPassword?.text.toString()

                retrofitClient?.logIn(username, password, object : BooleanCallback {
                    override fun onSuccess(value: Boolean) {
                        Toast.makeText(applicationContext, "Successfully signed in!", Toast.LENGTH_LONG).show()
                        app?.username = username;
                        app?.password = password;
                        val intentGame = Intent(applicationContext, MenuActivity::class.java)
                        startActivity(intentGame)
                    }

                    override fun onFailure() {
                        Toast.makeText(applicationContext, "Invalid Credentials!", Toast.LENGTH_LONG).show()
                    }
                })
            }
            R.id.btn_forgot_pass -> {
                forgotPasswordEmail()
            }
        }
    }

    private fun forgotPasswordEmail() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Please enter your username")
        var username: String? = null

        // Set up the input
        val input = EditText(this)

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK", { dialog, which ->
            username = input.text.toString()

            retrofitClient?.sendForgotPasswordEmail(username, "Hilsen Gruppe 16 - DistributedHangman", object : StringCallback {
                override fun onSuccess(value: String?) {
                    Toast.makeText(applicationContext, "Your password has been sent. Please check your email.", Toast.LENGTH_LONG).show()
                }

                override fun onFailure() {
                    Toast.makeText(applicationContext, "Failed to send password to $username!", Toast.LENGTH_LONG).show()
                }
            })
        })
        builder.setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })
        builder.show()
    }

    private fun introCheck() {
        val key = "firstStart" + R.string.app_name

        /* Uncomment this line to getFromId the intro back */
        PreferenceManager.getDefaultSharedPreferences(baseContext).edit().remove(key).apply()

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