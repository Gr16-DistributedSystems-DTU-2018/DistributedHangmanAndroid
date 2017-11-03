package io.inabsentia.superhangman.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import io.inabsentia.superhangman.R

class PreGameActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_game_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /* Instantiate objects */
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)
        val btnWord: Button = findViewById(R.id.btn_pick_word)
        val btnRandom: Button = findViewById(R.id.btn_pick_random)

        /* Set OnClickListeners */
        btnWord.setOnClickListener(this)
        btnRandom.setOnClickListener(this)

        /* Set custom title */
        tvCustomTitle.text = "Choose a word!"
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        when (id) {
            R.id.btn_pick_word -> startActivity(Intent(this, PickWordActivity::class.java))
            R.id.btn_pick_random -> startActivity(Intent(this, GameActivity::class.java))
        }
    }

}