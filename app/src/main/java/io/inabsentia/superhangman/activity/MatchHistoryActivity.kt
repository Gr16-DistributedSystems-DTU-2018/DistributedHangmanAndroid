package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.adapter.MatchHistoryAdapter
import io.inabsentia.superhangman.data.dao.MatchDAO
import io.inabsentia.superhangman.data.dto.MatchDTO

class MatchHistoryActivity : AppCompatActivity() {

    private var tvCustomTitle: TextView? = null

    private var matchListView: ListView? = null
    private var matchAdapter: MatchHistoryAdapter? = null

    private var matchList: List<MatchDTO>? = null

    private val matchDAO = MatchDAO.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_history_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /*
         * Get all the high scores.
         */
        matchList = matchDAO!!.all

        /*
         * Instantiate objects.
         */
        tvCustomTitle = findViewById(R.id.action_bar_title)
        matchListView = findViewById(R.id.match_history_list)
        val emptyText: TextView = findViewById(R.id.empty_text)

        /* Remove placeholder text if list is not empty */
        if (matchList!!.isNotEmpty())
            emptyText.visibility = View.INVISIBLE

        matchAdapter = MatchHistoryAdapter(matchList!!, this)
        matchListView!!.adapter = matchAdapter

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.match_history_title_label)

        /*
         * Update the display.
         */
        updateDisplay()
    }

    private fun updateDisplay() {
        matchList = matchDAO!!.all
        matchListView!!.removeAllViewsInLayout()
    }

}