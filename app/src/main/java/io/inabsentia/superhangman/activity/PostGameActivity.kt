package io.inabsentia.superhangman.activity

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.singleton.App
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class PostGameActivity : AppCompatActivity(), View.OnClickListener {

    private var tvBodyStatus: TextView? = null
    private var tvCustomTitle: TextView? = null
    private var smileyImage: ImageView? = null
    private var btnContinue: Button? = null
    private var isWon = false

    private val app = App.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_game_activity)
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
            val mediaPlayer: MediaPlayer?

            if (isWon) {
                tvBodyStatus?.text = "You won!"

                showConfetti()
                smileyImage!!.setImageResource(R.drawable.happy_smiley)

                tvCustomTitle!!.setText(R.string.end_game_title_label_won)
            } else {
                tvBodyStatus?.text = "You lost!"

                smileyImage!!.setImageResource(R.drawable.sad_smiley)


                tvCustomTitle!!.setText(R.string.end_game_title_label_lost)

                /* Can't continue if you didn't win. */
                btnContinue!!.visibility = View.INVISIBLE
            }

        }

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_continue -> startActivity(Intent(this, GameActivity::class.java))
        }
    }

    override fun onBackPressed() {
       // app!!.recordHighScore(baseContext)
        //logic!!.reset()
        startActivity(Intent(this, MenuActivity::class.java))
    }

    /*
     * Konfetti Library
     * -- https://github.com/DanielMartinus/Konfetti/
     */
    private fun showConfetti() {
        val konfettiView = findViewById<View>(R.id.konfettiView) as KonfettiView
        konfettiView.build()
                .addColors(Color.CYAN, Color.BLUE, Color.BLUE)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(12, 5f))
                .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                .stream(300, 5000L)
    }

}