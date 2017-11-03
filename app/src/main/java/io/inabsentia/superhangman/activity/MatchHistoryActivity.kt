package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.data.dao.MatchDAO
import io.inabsentia.superhangman.data.dto.MatchDTO

class MatchHistoryActivity : AppCompatActivity(), View.OnClickListener {

    private var tvCustomTitle: TextView? = null
    private var btnReset: Button? = null

    private var matchListView: ListView? = null
    private var listViewAdapter: ArrayAdapter<*>? = null

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
        btnReset = findViewById(R.id.btn_reset_match_history)
        matchListView = findViewById(R.id.match_history_list)

        listViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, matchList!!)
        matchListView!!.adapter = listViewAdapter

        /* Set title of action bar */
        tvCustomTitle!!.setText(R.string.match_history_title_label)

        /*
         * Set I/O listeners.
         */
        btnReset!!.setOnClickListener(this)

        /*
         * Update the display.
         */
        updateDisplay()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_reset_match_history -> {
                resetHistory()
                updateDisplay()
            }
            else -> {
            }
        }
    }

    private fun updateDisplay() {
        matchList = matchDAO!!.all
        matchListView!!.removeAllViewsInLayout()
    }

    private fun resetHistory() {
        matchDAO!!.removeAll()
        matchDAO.save(applicationContext)
    }

}