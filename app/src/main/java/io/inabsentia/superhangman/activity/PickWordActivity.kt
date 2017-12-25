package io.inabsentia.superhangman.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.TextView
import io.inabsentia.superhangman.R
import io.inabsentia.superhangman.adapter.PickWordAdapter
import io.inabsentia.superhangman.logic.GameLogic

class PickWordActivity : AppCompatActivity() {

    private val logic = GameLogic.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pick_word_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        val words = logic!!.words

        /* Create objects */
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)
        val wordListView: ListView = findViewById(R.id.word_listview)
        val customAdapter = PickWordAdapter(words, this)
        wordListView.adapter = customAdapter

        /* Set action bar title */
        tvCustomTitle.setText(R.string.pick_a_word)

        /* Set WordList ClickListener */
        wordListView.setOnItemClickListener { parent, view, position, id ->
            val word = parent.getItemAtPosition(position) as String

            val intentGame = Intent(this, GameActivity::class.java)
            intentGame.putExtra("new_secret_word", word)
            startActivity(intentGame)
        }

    }

}