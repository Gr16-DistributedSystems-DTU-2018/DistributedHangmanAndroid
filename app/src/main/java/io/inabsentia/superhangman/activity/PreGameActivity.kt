package io.inabsentia.superhangman.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.inabsentia.superhangman.R
import java.util.*

class PreGameActivity : AppCompatActivity(), View.OnClickListener {

    private var preGameImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_game_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /* Instantiate objects */
        preGameImage = findViewById(R.id.pre_game_image)
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)
        val btnWord: Button = findViewById(R.id.btn_pick_word)
        val btnRandom: Button = findViewById(R.id.btn_pick_random)

        /* Set OnClickListeners */
        preGameImage!!.setOnClickListener(this)
        btnWord.setOnClickListener(this)
        btnRandom.setOnClickListener(this)

        /* Set custom title */
        tvCustomTitle.text = "Choose a word!"
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        when (id) {
            R.id.pre_game_image -> preGameImage!!.rotation = random.nextFloat() * MAXIMUM_IMAGE_ROT
            R.id.btn_pick_word -> {
                preGameImage!!.rotation = 0f
                startActivity(Intent(this, PickWordActivity::class.java))
            }
            R.id.btn_pick_random -> {
                preGameImage!!.rotation = 0f
                startActivity(Intent(this, GameActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {

    }

    companion object {
        private val random = Random()

        private val MAXIMUM_IMAGE_ROT = 5000
    }

}