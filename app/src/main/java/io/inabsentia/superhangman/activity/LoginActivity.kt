package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.inabsentia.superhangman.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var tvCustomTitle: TextView? = null

    private var btnLogin: Button? = null
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        tvCustomTitle = findViewById(R.id.action_bar_title)
        btnLogin = findViewById(R.id.btn_login)
        etUsername = findViewById(R.id.username_field)
        etPassword = findViewById(R.id.password_field)

        btnLogin!!.setOnClickListener(this)

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.welcome_title)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_login -> {
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
            }
        }
    }

}