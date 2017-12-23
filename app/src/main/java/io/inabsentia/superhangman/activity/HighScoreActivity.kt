package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.adapter.HighScoreAdapter
import io.inabsentia.superhangman.data.dao.HighScoreDAO
import io.inabsentia.superhangman.data.dto.HighScoreDTO

class HighScoreActivity : AppCompatActivity() {

    private val highScoreDAO = HighScoreDAO.instance
    private var highScoreList: List<HighScoreDTO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.high_score_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /* Get all scores */
        highScoreList = highScoreDAO!!.all

        /* Create objects */
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)
        val highScoreListView: ListView = findViewById(R.id.high_score_list)
        val emptyText: TextView = findViewById(R.id.empty_text)

        /* Remove placeholder text if list is not empty */
        if (highScoreList!!.isNotEmpty())
            emptyText.visibility = View.INVISIBLE

        val highScoreAdapter = HighScoreAdapter(highScoreList!!, this)
        highScoreListView.adapter = highScoreAdapter

        /* Set action bar title */
        tvCustomTitle.setText(R.string.high_score_title_label)

        updateDisplay(highScoreListView)
    }

    private fun updateDisplay(highScoreListView: ListView) {
        highScoreList = highScoreDAO!!.all
        highScoreListView.removeAllViewsInLayout()
    }

}