package io.inabsentia.superhangman.activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.logic.GameLogic
import io.inabsentia.superhangman.singleton.App

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var tvHiddenWord: TextView? = null
    private var tvScoreLabel: TextView? = null
    private var tvHighScore: TextView? = null
    private var tvCustomTitle: TextView? = null
    private var chronoMeter: Chronometer? = null
    private var hangmanImage: ImageView? = null

    private val BTN_AMOUNT = 28

    private val btnArray = arrayOfNulls<Button>(BTN_AMOUNT)
    private val btnIdArray = intArrayOf(R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d, R.id.btn_e, R.id.btn_f, R.id.btn_g, R.id.btn_h, R.id.btn_i, R.id.btn_j, R.id.btn_k, R.id.btn_l, R.id.btn_m, R.id.btn_n, R.id.btn_o, R.id.btn_p, R.id.btn_q, R.id.btn_r, R.id.btn_s, R.id.btn_t, R.id.btn_u, R.id.btn_v, R.id.btn_w, R.id.btn_x, R.id.btn_y, R.id.btn_z, R.id.btn_hint_1, R.id.btn_hint_2)

    private val app = App.instance
    private val logic = GameLogic.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /*
         * Instantiate objects.
         */
        tvHiddenWord = findViewById(R.id.hidden_word)
        tvScoreLabel = findViewById(R.id.high_score_label)
        tvHighScore = findViewById(R.id.high_score)
        tvCustomTitle = findViewById(R.id.action_bar_title)
        chronoMeter = findViewById(R.id.time)
        hangmanImage = findViewById(R.id.game_image)

        for (i in btnArray.indices) {
            btnArray[i] = findViewById(btnIdArray[i])
            btnArray[i]!!.setOnClickListener(this)
        }

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.app_name)

        /* Set title color */
        tvCustomTitle!!.setTextColor(resources.getColor(R.color.textColor))

        /*
         * Initialize the chronometer.
         */
        chronoMeter!!.base = SystemClock.elapsedRealtime()
        chronoMeter!!.start()

        /*
         * We have to initialize the logic controller
         * before using it's functionality.
         *
         * If a custom word was picked,
         * put it as the new secret word.
         */
        val extras = intent.extras

        if (extras != null)
            logic!!.init(extras.getString("new_secret_word"))
        else
            logic!!.init()

        /*
         * If the logic controller initializing has gone well,
         * the display is updated with new values and text.
         */
        updateDisplay()

        /*
         * Remove Keyboard
         */
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    private fun updateDisplay() {
        /* Update the current hidden word */
        tvHiddenWord!!.text = logic!!.hiddenWord

        /* Update the current score */
        tvHighScore!!.text = logic.score.toString()

        /* Update the image according to the amount of lives left */
        when (logic.life) {
            7 -> hangmanImage!!.setImageResource(R.drawable.wrong_0)
            6 -> hangmanImage!!.setImageResource(R.drawable.wrong_1)
            5 -> hangmanImage!!.setImageResource(R.drawable.wrong_2)
            4 -> hangmanImage!!.setImageResource(R.drawable.wrong_3)
            3 -> hangmanImage!!.setImageResource(R.drawable.wrong_4)
            2 -> hangmanImage!!.setImageResource(R.drawable.wrong_5)
            1 -> hangmanImage!!.setImageResource(R.drawable.wrong_6)
        }
    }

    private fun guess(guess: Char) {
        /* Make the logic controller take a guess! */
        logic!!.guess(guess)

        /* Update the display, to see the next status after the guess */
        updateDisplay()

        /* Check whether the game is lost or not */
        if (logic.isLost) firePostGameActivity(false)

        /* Check whether the game is won or not */
        if (logic.isWon) firePostGameActivity(true)
    }

    private fun firePostGameActivity(isWon: Boolean) {
        val intentPostGame = Intent(this, PostGameActivity::class.java)
        val secretWord = logic?.secretWord

        intentPostGame.putExtra("game_status", isWon)
        intentPostGame.putExtra("secret_word", secretWord)
        intentPostGame.putExtra("round_count", logic!!.rounds)

        calculateTimeUsed()
        app!!.recordMatch(baseContext)

        if (!isWon) logic.reset()
        startActivity(intentPostGame)
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.btn_hint_1 || id == R.id.btn_hint_2) {
            if (logic!!.score > 0) {
                giveHint(view)
            } else {
                val snackbar: Snackbar = Snackbar.make(view, getString(R.string.high_score_not_high_enough), Snackbar.LENGTH_SHORT)
                snackbar.setActionTextColor(resources.getColor(R.color.textColor))
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorAccent))
                snackbar.show()
            }
        } else {
            Log.d("btnClick", (view as Button).text.toString().toLowerCase())
            guess(view.text.toString().toLowerCase()[0])

            val btnAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.btn_fade)
            view.startAnimation(btnAnim)

            view.setTextColor(resources.getColor(R.color.primaryColor))
            view.isEnabled = false
        }
    }

    private fun calculateTimeUsed() {
        val secondsElapsed = (SystemClock.elapsedRealtime() - chronoMeter!!.base) / 1000.0
        logic!!.timeUsed = secondsElapsed + logic.timeUsed
    }

    private fun giveHint(view: View) {
        logic!!.giveHint()

        updateDisplay()

        /* Check whether the game is lost or not */
        if (logic.isLost) firePostGameActivity(false)

        /* Check whether the game is won or not */
        if (logic.isWon) firePostGameActivity(true)

        view.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        // HighScore
        app!!.recordHighScore(baseContext)

        calculateTimeUsed()
        app.recordMatch(baseContext)

        logic!!.reset()
        startActivity(Intent(this, MenuActivity::class.java))
    }

}