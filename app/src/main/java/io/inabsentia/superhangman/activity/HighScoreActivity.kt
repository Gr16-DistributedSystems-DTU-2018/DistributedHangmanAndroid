package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import brugerautorisation.data.Bruger
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.adapter.HighScoreAdapter
import io.inabsentia.superhangman.data.dao.HighScoreDAO
import io.inabsentia.superhangman.data.dto.HighScoreDTO
import io.inabsentia.superhangman.retrofit.RetrofitClient

class HighScoreActivity : AppCompatActivity() {

    private val highScoreDAO = HighScoreDAO.instance
    private var highScoreList: List<HighScoreDTO>? = null

    private var retrofitClient: RetrofitClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.high_score_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
        retrofitClient = RetrofitClient(this)

        /* Get all scores */
        highScoreList = highScoreDAO!!.all

        /* Create objects */
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)
        val scoreText: TextView = findViewById(R.id.score_view)

        retrofitClient!!.getCurrentUser(object {
            override fun onSuccess(user: Bruger?) {
                val username: String? = user?.brugernavn
                val password: String? = user?.adgangskode

                retrofitClient?.getUserField(username, password, "s151641_highscore", object {
                    override fun onSuccess(value: String?) {
                        scoreText.text = username + " highscore: " + value;
                    }

                    override fun onFailure() {
                        Toast.makeText(applicationContext, "Failed to user highscore!", Toast.LENGTH_LONG).show()
                    }

                })

            }

            override fun onFailure() {
                Toast.makeText(applicationContext, "Failed to get current user!", Toast.LENGTH_LONG).show()
            }

        })

        /* Remove placeholder text if list is not empty */
        val highScoreAdapter = HighScoreAdapter(highScoreList!!, this)

        /* Set action bar title */
        tvCustomTitle.setText(R.string.high_score_title_label)
    }

}