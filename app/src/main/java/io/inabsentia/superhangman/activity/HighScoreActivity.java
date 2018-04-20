package io.inabsentia.superhangman.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.inabsentia.superhangman.R;

public class HighScoreActivity extends AppCompatActivity {

    private TextView tvCustomTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        tvCustomTitle = findViewById(R.id.action_bar_title);
        tvCustomTitle.setText("High Scores");
    }


}