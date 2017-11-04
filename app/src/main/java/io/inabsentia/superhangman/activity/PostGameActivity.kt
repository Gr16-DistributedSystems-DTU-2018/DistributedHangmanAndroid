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
import io.inabsentia.superhangman.logic.GameLogic

class PostGameActivity : AppCompatActivity(), View.OnClickListener {

    private var tvBodyStatus: TextView? = null
    private var tvCustomTitle: TextView? = null
    private var smileyImage: ImageView? = null
    private var btnContinue: Button? = null
    private var isWon = false

    private val logic = GameLogic.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_game_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /*
         * Instantiate objects.
         */
        tvBodyStatus = findViewById(R.id.status_body)
        tvCustomTitle = findViewById(R.id.action_bar_title)
        smileyImage = findViewById(R.id.smiley_image)
        btnContinue = findViewById(R.id.btn_continue)

        /*
         * Set I/O listeners.
         */
        btnContinue!!.setOnClickListener(this)

        /*
         * Get intent from GameActivity.
         */
        val extras = intent.extras

        if (extras != null) {
            isWon = extras.getBoolean("game_status")
            val secretWord = extras.getString("secret_word")
            val totalGuessCount = extras.getInt("round_count")

            if (isWon) {
                smileyImage!!.setImageResource(R.drawable.happy_smiley)

                val bodyStatus = resources.getString(R.string.end_game_body_label_won, secretWord, totalGuessCount)
                tvBodyStatus!!.text = bodyStatus

                tvCustomTitle!!.setText(R.string.end_game_title_label_won)
            } else {
                smileyImage!!.setImageResource(R.drawable.sad_smiley)

                val bodyStatus = resources.getString(R.string.end_game_body_label_loss, secretWord)
                tvBodyStatus!!.text = bodyStatus

                tvCustomTitle!!.setText(R.string.end_game_title_label_lost)

                /* Can't continue if you didn't win. */
                btnContinue!!.visibility = View.INVISIBLE
            }

        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_continue -> startActivity(Intent(this, PreGameActivity::class.java))
        }
    }

    override fun onBackPressed() {
        logic!!.reset()
        startActivity(Intent(this, MenuActivity::class.java))
    }

}