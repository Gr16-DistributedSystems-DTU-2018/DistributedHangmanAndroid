package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dao.HighScoreDAO
import io.inabsentia.superhangman.data.dto.HighScoreDTO

class HighScoreActivity : AppCompatActivity() {

    private val highScoreDAO = HighScoreDAO.getInstance()
    private var highScoreList: List<HighScoreDTO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.high_score_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /* Get all scores */
        highScoreList = highScoreDAO.all

        /* Create objects */
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)
        val highScoreListView: ListView = findViewById(R.id.high_score_list)
        val btnReset: Button = findViewById(R.id.btn_reset_high_score)
        val listViewAdapter = ArrayAdapter<HighScoreDTO>(this, android.R.layout.simple_list_item_1, highScoreList)

        /* Set action bar title */
        tvCustomTitle.setText(R.string.high_score_title_label)

        /* Add btnReset ClickListener */
        btnReset.setOnClickListener { resetHighScores() }

        updateDisplay(highScoreListView)
    }

    private fun updateDisplay(highScoreListView: ListView) {
        highScoreList = highScoreDAO.all
        highScoreListView.removeAllViewsInLayout()
    }

    private fun resetHighScores() {
        highScoreDAO.removeAll()
        highScoreDAO.save(this)
    }

}