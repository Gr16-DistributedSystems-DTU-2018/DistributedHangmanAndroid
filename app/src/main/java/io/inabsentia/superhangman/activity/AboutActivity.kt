package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import io.inabsentia.superhangman.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        /* Instantiate objects */
        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)

        /* Set action bar title */
        tvCustomTitle.text = getString(R.string.about_title_label)
    }

}