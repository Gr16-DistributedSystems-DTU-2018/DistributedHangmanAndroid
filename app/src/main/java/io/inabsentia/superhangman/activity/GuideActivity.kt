package io.inabsentia.superhangman.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import io.inabsentia.superhangman.R

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guide_activity)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        val tvCustomTitle: TextView = findViewById(R.id.action_bar_title)

        /* Set title of action bar */
        tvCustomTitle.setText(R.string.guide_title_label)
    }

}