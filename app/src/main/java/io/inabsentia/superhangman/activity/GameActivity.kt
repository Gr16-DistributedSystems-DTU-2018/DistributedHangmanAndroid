package io.inabsentia.superhangman.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.retrofit.RetrofitClient
import io.inabsentia.superhangman.retrofit.interfaces.*
import io.inabsentia.superhangman.singleton.App

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var tvHiddenWord: TextView? = null
    private var tvScoreLabel: TextView? = null
    private var tvHighScore: TextView? = null
    private var tvCustomTitle: TextView? = null
    private var hangmanImage: ImageView? = null

    private val BTN_AMOUNT = 29

    private val btnArray = arrayOfNulls<Button>(BTN_AMOUNT)
    private val btnIdArray = intArrayOf(R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d, R.id.btn_e, R.id.btn_f, R.id.btn_g, R.id.btn_h, R.id.btn_i, R.id.btn_j, R.id.btn_k, R.id.btn_l, R.id.btn_m, R.id.btn_n, R.id.btn_o, R.id.btn_p, R.id.btn_q, R.id.btn_r, R.id.btn_s, R.id.btn_t, R.id.btn_u, R.id.btn_v, R.id.btn_w, R.id.btn_x, R.id.btn_y, R.id.btn_z, R.id.btn_ae, R.id.btn_oe, R.id.btn_aa)

    private val app = App.instance
    private var retrofitClient: RetrofitClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
        retrofitClient = RetrofitClient(this)

        retrofitClient?.resetScore(object : ResetScoreCallback {
            override fun onSuccess() {
                Toast.makeText(applicationContext, "Successfully reset score!", Toast.LENGTH_LONG).show()

                retrofitClient?.resetGame(object : ResetGameCallback {
                    override fun onSuccess() {
                        updateDisplay()
                        Toast.makeText(applicationContext, "Successfully reset game!", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure() {
                        Toast.makeText(applicationContext, "Failed to reset game!", Toast.LENGTH_LONG).show()
                    }
                })
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to reset score!", Toast.LENGTH_LONG).show()
            }
        })

        /*
         * Instantiate objects.
         */
        tvHiddenWord = findViewById(R.id.hidden_word)
        tvScoreLabel = findViewById(R.id.high_score_label)
        tvHighScore = findViewById(R.id.high_score)
        tvCustomTitle = findViewById(R.id.action_bar_title)
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

        /*
         * If the logic controller initializing has gone well,
         * the display is updated with new values and text.
         */
        //updateDisplay()

        /*
         * Remove Keyboard
         */
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    private fun updateDisplay() {
        retrofitClient?.getGuessedWord(object : GetGuessedWordCallback {
            override fun onSuccess(value: String?) {
                tvHiddenWord!!.text = value

                retrofitClient?.getScore(object : GetScoreCallback {
                    override fun onSuccess(score: Int) {
                        tvHighScore!!.text = score.toString()

                        /* Update the image according to the amount of lives left */
                        retrofitClient?.getLife(object : GetLifeCallback {
                            override fun onSuccess(life: Int) {
                                when (life) {
                                    7 -> hangmanImage!!.setImageResource(R.drawable.wrong_0)
                                    6 -> hangmanImage!!.setImageResource(R.drawable.wrong_1)
                                    5 -> hangmanImage!!.setImageResource(R.drawable.wrong_2)
                                    4 -> hangmanImage!!.setImageResource(R.drawable.wrong_3)
                                    3 -> hangmanImage!!.setImageResource(R.drawable.wrong_4)
                                    2 -> hangmanImage!!.setImageResource(R.drawable.wrong_5)
                                    1 -> hangmanImage!!.setImageResource(R.drawable.wrong_6)
                                }
                            }

                            override fun onFailure() {
                                Toast.makeText(applicationContext, "GetLife failed!", Toast.LENGTH_LONG).show()
                            }
                        })


                    }

                    override fun onFailure() {
                        Toast.makeText(applicationContext, "GetScore Failed!", Toast.LENGTH_LONG).show()
                    }
                })

            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "GetGuessedWord: Failed!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun guess(guess: Char) {
        /* Make the logic controller take a guess! */
        retrofitClient!!.guess(guess, object : GuessCallback {
            override fun onSuccess(value: Boolean) {

            }

            override fun onFailure() {

            }

        })

        /* Update the display, to see the next status after the guess */
        updateDisplay()

        /* Check whether the game is lost or not */

        retrofitClient!!.isGameLost(object : IsGameLostCallback {
            override fun onSuccess(value: Boolean) {
                if (value) firePostGameActivity(false)
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "IsGameLost: Failed!", Toast.LENGTH_LONG).show()
            }
        })


        retrofitClient!!.isGameWon(object : IsGameWonCallback {
            override fun onSuccess(value: Boolean) {
                if (value) firePostGameActivity(true)
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "IsGameWon: Failed!", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun firePostGameActivity(isWon: Boolean) {
        val intentPostGame = Intent(this, PostGameActivity::class.java)

        intentPostGame.putExtra("game_status", isWon)
        app!!.recordMatch(baseContext)

        //if (!isWon) logic.reset()
        startActivity(intentPostGame)
    }

    override fun onClick(view: View) {
        val id = view.id
        if (id == R.id.btn_hint_1 || id == R.id.btn_hint_2) {
            /*
            if (logic!!.score > 0) {
                giveHint(view)

            } else {
                val snackbar: Snackbar = Snackbar.make(view, getString(R.string.high_score_not_high_enough), Snackbar.LENGTH_SHORT)
                snackbar.setActionTextColor(resources.getColor(R.color.textColor))
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorAccent))
                snackbar.show()
            }
            */
        } else {
            Log.d("btnClick", (view as Button).text.toString().toLowerCase())
            guess(view.text.toString().toLowerCase()[0])

            val btnAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.btn_fade)
            view.startAnimation(btnAnim)

            view.setTextColor(resources.getColor(R.color.primaryColor))
            view.isEnabled = false
        }
    }


    override fun onBackPressed() {
        // HighScore
        app!!.recordHighScore(baseContext)
        app.recordMatch(baseContext)
        startActivity(Intent(this, MenuActivity::class.java))
    }

}