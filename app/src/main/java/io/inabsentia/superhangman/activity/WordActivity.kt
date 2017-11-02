package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.TextView
import io.inabsentia.superhangman.R

class WordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /* Create objects */
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)
        val wordList: ListView = findViewById(R.id.word_list)

        /* Set action bar title */
        tvCustomTitle.setText(R.string.pick_a_word)


    }

}