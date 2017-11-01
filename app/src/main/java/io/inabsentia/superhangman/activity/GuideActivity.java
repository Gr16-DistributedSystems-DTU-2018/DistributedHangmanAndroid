package io.inabsentia.superhangman.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.inabsentia.superhangman.R;

public class GuideActivity extends AppCompatActivity {

    TextView tvCustomTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        tvCustomTitle = (TextView) findViewById(R.id.action_bar_title);

        /* Set title of action bar */
        tvCustomTitle.setText(R.string.guide_title_label);
    }

}