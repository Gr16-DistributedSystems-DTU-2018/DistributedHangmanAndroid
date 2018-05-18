package io.inabsentia.superhangman.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
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
import io.inabsentia.superhangman.retrofit.interfaces.BooleanCallback
import io.inabsentia.superhangman.retrofit.interfaces.IntegerCallback
import io.inabsentia.superhangman.retrofit.interfaces.StringCallback
import io.inabsentia.superhangman.singleton.App

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var tvHiddenWord: TextView? = null
    private var tvScoreLabel: TextView? = null
    private var tvHighScore: TextView? = null
    private var tvLife: TextView? = null
    private var tvCustomTitle: TextView? = null
    private var hangmanImage: ImageView? = null
    private var btnBack: Button? = null

    private val BTN_AMOUNT = 29

    private val btnArray = arrayOfNulls<Button>(BTN_AMOUNT)
    private val btnIdArray = intArrayOf(R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d, R.id.btn_e, R.id.btn_f, R.id.btn_g, R.id.btn_high_scores, R.id.btn_i, R.id.btn_j, R.id.btn_k, R.id.btn_l, R.id.btn_m, R.id.btn_n, R.id.btn_o, R.id.btn_p, R.id.btn_q, R.id.btn_r, R.id.btn_s, R.id.btn_t, R.id.btn_u, R.id.btn_v, R.id.btn_w, R.id.btn_x, R.id.btn_y, R.id.btn_z, R.id.btn_ae, R.id.btn_oe, R.id.btn_aa)

    private val app: App? = App.getInstance()
    private var retrofitClient: RetrofitClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
        retrofitClient = RetrofitClient(this)

        /*
         * Instantiate objects.
         */
        tvHiddenWord = findViewById(R.id.hidden_word)
        tvScoreLabel = findViewById(R.id.high_score_label)
        tvHighScore = findViewById(R.id.high_score)
        tvLife = findViewById(R.id.life_value);
        tvCustomTitle = findViewById(R.id.action_bar_title)
        hangmanImage = findViewById(R.id.game_image)
        btnBack = findViewById(R.id.btn_back)

        btnBack?.setOnClickListener(this)

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
        updateDisplay()

        /*
         * Remove Keyboard
         */
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    private fun updateDisplay() {
        // update word
        retrofitClient?.getWordWord(app?.username, object : StringCallback {
            override fun onSuccess(value: String?) {
                tvHiddenWord?.text = value
                // update score
                retrofitClient?.getScore(app?.username, object : IntegerCallback {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(value: Int) {
                        tvHighScore?.text = Integer.toString(value)

                        // update life
                        retrofitClient?.getLife(app?.username, object : IntegerCallback {
                            @SuppressLint("SetTextI18n")
                            override fun onSuccess(value: Int) {
                                tvLife?.text = Integer.toString(value)
                                when (value) {
                                    6 -> hangmanImage!!.setImageResource(R.drawable.wrong_0)
                                    5 -> hangmanImage!!.setImageResource(R.drawable.wrong_1)
                                    4 -> hangmanImage!!.setImageResource(R.drawable.wrong_2)
                                    3 -> hangmanImage!!.setImageResource(R.drawable.wrong_3)
                                    2 -> hangmanImage!!.setImageResource(R.drawable.wrong_4)
                                    1 -> hangmanImage!!.setImageResource(R.drawable.wrong_5)
                                    0 -> hangmanImage!!.setImageResource(R.drawable.wrong_6)
                                }

                            }

                            override fun onFailure() {
                                Toast.makeText(applicationContext, "Failed to fetch life!", Toast.LENGTH_LONG).show()
                            }
                        })
                    }

                    override fun onFailure() {
                        Toast.makeText(applicationContext, "Failed to fetch score!", Toast.LENGTH_LONG).show()
                    }
                })
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to fetch word!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun resetGame() {
        retrofitClient?.resetGame(app?.username, object : StringCallback {
            override fun onSuccess(value: String?) {
                Toast.makeText(applicationContext, "Successfully reset game!", Toast.LENGTH_LONG).show()
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to reset game!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun resetScore() {
        retrofitClient?.resetScore(app?.username, object : StringCallback {
            override fun onSuccess(value: String?) {
                Toast.makeText(applicationContext, "Successfully reset score!", Toast.LENGTH_LONG).show()
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to reset score!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun reset() {
        resetGame()
        resetScore()
    }

    private fun guess(guess: Char) {
        retrofitClient?.guess(app?.username, guess, object : BooleanCallback {
            override fun onSuccess(value: Boolean) {
                updateDisplay()
            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to guess " + guess + "!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun firePostGameActivity(isWon: Boolean) {
        val intentPostGame = Intent(this, PostGameActivity::class.java)
        intentPostGame.putExtra("game_status", isWon)
        startActivity(intentPostGame)
    }

    override fun onClick(view: View) {
        val id = view.id

        if (id == R.id.btn_back) {
            AlertDialog.Builder(this)
                    .setTitle("Back")
                    .setMessage("Are you sure you want to go back? Progress will be lost!")
                    .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        // do the acknowledged action, beware, this is run on UI thread
                        startActivity(Intent(this, MenuActivity::class.java))
                        super.onBackPressed()
                    }.create().show()
        } else {
            Log.d("btnClick", (view as Button).text.toString().toLowerCase())
            guess(view.text.toString().toLowerCase()[0])
            val btnAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.btn_fade)
            view.startAnimation(btnAnim)
            view.setTextColor(resources.getColor(R.color.primaryColor))
            view.isEnabled = false

            retrofitClient?.isGameLost(app?.username, object : BooleanCallback {
                override fun onSuccess(value: Boolean) {
                    if (value) {
                        setHighscore()
                        reset()
                        firePostGameActivity(false)
                    } else {
                        retrofitClient?.isGameWon(app?.username, object : BooleanCallback {
                            override fun onSuccess(value: Boolean) {
                                if (value) {
                                    setHighscore()
                                    resetGame()
                                    firePostGameActivity(true)
                                }
                            }

                            override fun onFailure() {

                            }
                        })
                    }
                }

                override fun onFailure() {

                }
            })
        }
    }

    private fun setHighscore() {
        retrofitClient?.isHighscore(app?.username, object : BooleanCallback {
            override fun onSuccess(value: Boolean) {
                if (value) {

                    retrofitClient?.getScore(app?.username, object : IntegerCallback {
                        override fun onSuccess(value: Int) {
                            val highscore = value

                            retrofitClient?.setUserHighscore(app?.username, Integer.toString(value), object : BooleanCallback {
                                override fun onSuccess(value: Boolean) {
                                    Toast.makeText(applicationContext, "New highscore: $highscore!", Toast.LENGTH_LONG).show()
                                }

                                override fun onFailure() {

                                }
                            })

                        }

                        override fun onFailure() {

                        }
                    })

                }
            }

            override fun onFailure() {

            }
        })
    }

    override fun onBackPressed() {
        // HighScore
        //app!!.recordHighScore(baseContext)
        //app.recordMatch(baseContext)
        startActivity(Intent(this, MenuActivity::class.java))
    }

}